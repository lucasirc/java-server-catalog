package com.lucasirc.servercatalog.resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.service.ApplicationResourceService;
import com.lucasirc.servercatalog.service.ServerResourceService;
import com.lucasirc.servercatalog.service.v1.ApplicationV1Transformer;
import com.lucasirc.servercatalog.service.v1.ServerV1Transformer;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

}
