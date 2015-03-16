package com.lucasirc.servercatalog.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.model.Server;
import com.lucasirc.servercatalog.service.ApplicationResourceService;
import com.lucasirc.servercatalog.service.ServerResourceService;
import com.lucasirc.servercatalog.service.v1.ApplicationV1Transformer;
import com.lucasirc.servercatalog.service.v1.ServerV1Transformer;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApplicationResourceTests {


    @Test
    public void testGet() {
        Application app = new Application(13l, "game 123", "lucas");
        Map appMap = new ApplicationV1Transformer().entityToMap(app);

        ApplicationResourceService appspy = Mockito.spy(new ApplicationResourceService(null,null));
        Mockito.doReturn(appMap).when(appspy).get(13l);

        ApplicationResource resource = new ApplicationResource();

        ApplicationResource resourceS = Mockito.spy(resource);
        Mockito.doReturn(appspy).when(resourceS).getAppResourceService();

        Response response = resourceS.get(13l);


        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());

        Object obj = response.getEntity();
        assertTrue(obj instanceof String);

        JsonObject objJson = new JsonParser().parse((String) obj).getAsJsonObject();
        assertEquals(13l, objJson.get("id").getAsLong());
        assertEquals("game 123", objJson.get("name").getAsString());
        assertEquals("lucas", objJson.get("owner").getAsString());

    }
    @Test
    public void testGet_not_found() {
        ApplicationResourceService appspy = Mockito.spy(new ApplicationResourceService(null,null));
        Mockito.doReturn(null).when(appspy).get(13l);

        ApplicationResource resource = new ApplicationResource();

        ApplicationResource resourceS = Mockito.spy(resource);
        Mockito.doReturn(appspy).when(resourceS).getAppResourceService();

        Response response = resourceS.get(13l);


        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
    }

    /**
     * POST Tests
     */
    @Test
    public void testPost() throws URISyntaxException {
        Application app = new Application(13l, "teste", "teste2");
        ApplicationResourceService appSpy = Mockito.spy(new ApplicationResourceService(null, null));
        Mockito.doReturn(app).when(appSpy).create("{\"name\": \"teste\"}");

        ApplicationResource resourceS = Mockito.spy( new ApplicationResource());
        Mockito.doReturn(appSpy).when(resourceS).getAppResourceService();

        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(new URI("path_get"));

        resourceS.uriInfo = uriInfo;

        Response response = resourceS.post("{\"name\": \"teste\"}");

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("path_get/13", response.getHeaders().get("Location").get(0).toString());

    }


    /**
     * PUT Tests
     */
    @Test
    public void testPut() throws URISyntaxException {
        Application app = new Application(145l, "teste", "teste2");
        ApplicationResourceService appSpy = Mockito.spy(new ApplicationResourceService(null, null));
        Mockito.doReturn(app).when(appSpy).update(145l, "{\"name\": \"teste\"}");

        ApplicationResource resourceS = Mockito.spy( new ApplicationResource());
        Mockito.doReturn(appSpy).when(resourceS).getAppResourceService();

        Response response = resourceS.put("{\"name\": \"teste\"}", 145);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testPut_not_found() {
        ApplicationResourceService appSpy = Mockito.spy(new ApplicationResourceService(null, null));
        Mockito.doReturn(null).when(appSpy).update(145l, "{\"name\": \"teste\"}");

        ApplicationResource resourceS = Mockito.spy( new ApplicationResource());
        Mockito.doReturn(appSpy).when(resourceS).getAppResourceService();

        Response response = resourceS.put("{\"name\": \"teste\"}", 145);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }


    @Test
    public void testList()  {
        List<Map> servers = new ArrayList<Map>();
        Map appMap = new ApplicationV1Transformer().entityToMap(new Application(13l, "game 1","lucas"));
        servers.add(appMap);
        appMap = new ApplicationV1Transformer().entityToMap(new Application(11l, "game 2", "jose"));
        servers.add(appMap);

        ApplicationResourceService appSpy = Mockito.spy(new ApplicationResourceService(null, null));
        Mockito.doReturn(servers).when(appSpy).list(0l, 10l);

        ApplicationResource resourceS = Mockito.spy( new ApplicationResource());
        Mockito.doReturn(appSpy).when(resourceS).getAppResourceService();

        Response response = resourceS.list(0l, 10l);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());


        Object obj = response.getEntity();
        assertTrue(obj instanceof String);

        JsonArray objJson = new JsonParser().parse((String) obj).getAsJsonArray();
        assertEquals(2, objJson.size());

        assertEquals(13l, objJson.get(0).getAsJsonObject().get("id").getAsLong());
        assertEquals("game 1", objJson.get(0).getAsJsonObject().get("name").getAsString());
        assertEquals("lucas", objJson.get(0).getAsJsonObject().get("owner").getAsString());

        assertEquals(11l, objJson.get(1).getAsJsonObject().get("id").getAsLong());
        assertEquals("game 2", objJson.get(1).getAsJsonObject().get("name").getAsString());
        assertEquals("jose", objJson.get(1).getAsJsonObject().get("owner").getAsString());
    }


    @Test
    public void testDelete() {
        Application app = new Application(1l, "a","b");

        ApplicationResourceService appSpy = Mockito.spy(new ApplicationResourceService(null, null));
        Mockito.doReturn(app).when(appSpy).delete(1l);

        ApplicationResource resourceS = Mockito.spy( new ApplicationResource());
        Mockito.doReturn(appSpy).when(resourceS).getAppResourceService();

        Response response = resourceS.delete(1);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }


    @Test
    public void testDelete_not_found() {
        ApplicationResourceService appSpy = Mockito.spy(new ApplicationResourceService(null, null));
        Mockito.doReturn(null).when(appSpy).delete(145l);

        ApplicationResource resourceS = Mockito.spy( new ApplicationResource());
        Mockito.doReturn(appSpy).when(resourceS).getAppResourceService();

        Response response = resourceS.delete(145);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

}
