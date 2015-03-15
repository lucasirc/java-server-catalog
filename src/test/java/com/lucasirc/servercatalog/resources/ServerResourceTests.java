package com.lucasirc.servercatalog.resources;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.model.Server;
import com.lucasirc.servercatalog.service.ServerResourceService;

import com.lucasirc.servercatalog.service.v1.ServerV1Transformer;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.Assert.*;

public class ServerResourceTests {
    /**
     * LIST TESTS
     */
    @Test
    public void testList() {

        List<Map> servers = new ArrayList<Map>();
        Map serverMap = new ServerV1Transformer().entityToMap(new Server(13l, "gamehost"));
        servers.add(serverMap);

        ServerResourceService srsSpy = Mockito.spy(new ServerResourceService(null, null));
        Mockito.doReturn(servers).when(srsSpy).list(0, 10);

        ServerResource resource = new ServerResource();

        ServerResource resourceS = Mockito.spy(resource);
        Mockito.doReturn(srsSpy).when(resourceS).getResourceService();

        Response response = resourceS.list(0, 10);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());

        Object obj = response.getEntity();
        assertTrue(obj instanceof String);

        JsonArray objJson = new JsonParser().parse((String) obj).getAsJsonArray();
        assertEquals(1, objJson.size());
        assertEquals(13l, objJson.get(0).getAsJsonObject().get("id").getAsLong());
        assertEquals("gamehost", objJson.get(0).getAsJsonObject().get("hostname").getAsString());
    }

    @Test
    public void testList_error() {
        ServerResourceService srsSpy = Mockito.spy(new ServerResourceService(null, null));
        Mockito.doThrow(new RuntimeException()).when(srsSpy).list(0, 10);

        ServerResource resource = new ServerResource();

        ServerResource resourceS = Mockito.spy(resource);
        Mockito.doReturn(srsSpy).when(resourceS).getResourceService();

        Response response = resourceS.list(0, 10);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    /**
     * GET TESTS
     */

    @Test
    public void testGet() {
        Server server = new Server(13l, "gamehost");
        server.getApps().add(new Application(123l, "game 1", "lucas"));
        server.getApps().add(new Application(456l, "game 2", "jose"));


        Map serverMap = new ServerV1Transformer().entityToMap(server);

        ServerResourceService srsSpy = Mockito.spy(new ServerResourceService(null, null));
        Mockito.doReturn(serverMap).when(srsSpy).get(13l);

        ServerResource resource = new ServerResource();

        ServerResource resourceS = Mockito.spy(resource);
        Mockito.doReturn(srsSpy).when(resourceS).getResourceService();

        Response response = resourceS.get(13l);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());

        Object obj = response.getEntity();
        assertTrue(obj instanceof String);

        JsonObject objJson = new JsonParser().parse((String) obj).getAsJsonObject();

        assertEquals(13l, objJson.get("id").getAsLong());
        assertEquals("gamehost", objJson.get("hostname").getAsString());

        JsonArray apps = objJson.get("apps").getAsJsonArray();
        assertNotNull(apps);
        assertEquals(2, apps.size());

        JsonObject app1 = apps.get(0).getAsJsonObject();
        assertEquals(123l, app1.get("id").getAsLong());
        assertEquals("game 1", app1.get("name").getAsString());
        assertEquals("lucas", app1.get("owner").getAsString());

        JsonObject app2 = apps.get(1).getAsJsonObject();
        assertEquals(456l, app2.get("id").getAsLong());
        assertEquals("game 2", app2.get("name").getAsString());
        assertEquals("jose", app2.get("owner").getAsString());
    }

    @Test
    public void testGet_not_found() {
        ServerResourceService srsSpy = Mockito.spy(new ServerResourceService(null, null));
        Mockito.doReturn(null).when(srsSpy).get(13l);

        ServerResource resource = new ServerResource();

        ServerResource resourceS = Mockito.spy(resource);
        Mockito.doReturn(srsSpy).when(resourceS).getResourceService();

        Response response = resourceS.get(13l);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());

    }
    /**
     * POST Tests
     */
    @Test
    public void testPost() throws URISyntaxException {
        Server server = new Server(13l, "");
        ServerResourceService srsSpy = Mockito.spy(new ServerResourceService(null, null));
        Mockito.doReturn(server).when(srsSpy).create("{\"id\": 13}");

        ServerResource resource = new ServerResource();

        ServerResource resourceS = Mockito.spy(resource);
        Mockito.doReturn(srsSpy).when(resourceS).getResourceService();

        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(new URI("path_get"));

        resourceS.uriInfo = uriInfo;

        Response response = resourceS.post("{\"id\": 13}");

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("path_get/13", response.getHeaders().get("Location").get(0).toString());

    }

    /**
     * PUT Tests
     */
    @Test
    public void testPut() throws URISyntaxException {
        Server server = new Server(13l, "");
        ServerResourceService srsSpy = Mockito.spy(new ServerResourceService(null, null));
        Mockito.doReturn(server).when(srsSpy).update(13l, "{\"hostname\": \"gamehostname\"}");

        ServerResource resource = new ServerResource();

        ServerResource resourceS = Mockito.spy(resource);
        Mockito.doReturn(srsSpy).when(resourceS).getResourceService();

        Response response = resourceS.put("{\"hostname\": \"gamehostname\"}", 13l);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testPut_not_found()  {
        ServerResourceService srsSpy = Mockito.spy(new ServerResourceService(null, null));
        Mockito.doReturn(null).when(srsSpy).update(13l, "{\"hostname\": \"gamehostname\"}");

        ServerResource resource = new ServerResource();

        ServerResource resourceS = Mockito.spy(resource);
        Mockito.doReturn(srsSpy).when(resourceS).getResourceService();

        Response response = resourceS.put("{\"hostname\": \"gamehostname\"}", 13l);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }


    @Test
    public void testDelete()  {
        Server server = new Server(13l, "");
        ServerResourceService srsSpy = Mockito.spy(new ServerResourceService(null, null));
        Mockito.doReturn(server).when(srsSpy).delete(13l);

        ServerResource resource = new ServerResource();

        ServerResource resourceS = Mockito.spy(resource);
        Mockito.doReturn(srsSpy).when(resourceS).getResourceService();

        Response response = resourceS.delete(13l);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }


    @Test
    public void testDelete_not_found()  {
        ServerResourceService srsSpy = Mockito.spy(new ServerResourceService(null, null));
        Mockito.doReturn(null).when(srsSpy).delete(13l);

        ServerResource resource = new ServerResource();

        ServerResource resourceS = Mockito.spy(resource);
        Mockito.doReturn(srsSpy).when(resourceS).getResourceService();

        Response response = resourceS.delete(13l);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }



}