package com.lucasirc.servercatalog.service.v1;

import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.model.Server;
import com.lucasirc.servercatalog.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ServerV1TransformerTests {

    @Test
    public void testEntityToMap_0_apps() {
        Server server = new Server(15l, "gamehost");

        Map map = new ServerV1Transformer().entityToMap(server);

        assertEquals(map.get("id"), 15l);
        assertEquals(map.get("hostname"), "gamehost");
        Assert.assertTrue(map.get("apps") instanceof List);
        assertEquals(0,((List) map.get("apps")).size());
    }
    @Test
    public void testEntityToMap_with_apps() {
        Server server = new Server(15l, "gamehost");
        server.getApps().add(new Application(14l, "gamex1", "lucasirc"));
        server.getApps().add(new Application(16l, "gamex2", "lucasirc"));

        Map map = new ServerV1Transformer().entityToMap(server);

        assertEquals(map.get("id"), 15l);
        assertEquals(map.get("hostname"), "gamehost");
        Assert.assertTrue(map.get("apps") instanceof List);

        List<Map> apps = (List) map.get("apps");

        assertEquals(2,apps.size()) ;

        Map app1 = apps.get(0);
        assertEquals(14l,app1.get("id"));
        assertEquals("gamex1",app1.get("name"));
        assertEquals("lucasirc",app1.get("owner"));
    }


    @Test
    public void testContentToEntity_2_apps() throws IOException {

        String app_v1 = TestUtils.getTestResourceAsString("server_v1.json");
        System.out.println("Content: "+ app_v1);
        Assert.assertNotNull(app_v1);


        Server server = new ServerV1Transformer().contentToEntity(app_v1);

        Assert.assertNotNull(server);
        assertEquals(new Long(12l), server.getId());
        assertEquals("gamexserver", server.getHostname());

        List<Application> apps  = server.getApps();
        assertNotNull(apps);
        assertEquals(2, apps.size());

        Application app = apps.get(0);

        assertEquals(new Long(123), app.getId());
        assertEquals("Game 1", app.getName());
        assertEquals("lucasirc", app.getOwner());
    }
    @Test
    public void testContentToEntity_0_apps() throws IOException {

        String app_v1 = TestUtils.getTestResourceAsString("server_v1_no_apps.json");
        System.out.println("Content: "+ app_v1);
        Assert.assertNotNull(app_v1);


        Server server = new ServerV1Transformer().contentToEntity(app_v1);

        Assert.assertNotNull(server);
        assertEquals(new Long(12l), server.getId());
        assertEquals("gamexserver", server.getHostname());

        List<Application> apps  = server.getApps();
        assertNull(apps);
    }

    @Test
    public void testContentToEntity_no_apps() throws IOException {

        String app_v1 = TestUtils.getTestResourceAsString("server_v1_no_apps.json");
        System.out.println("Content: "+ app_v1);
        Assert.assertNotNull(app_v1);


        Server server = new ServerV1Transformer().contentToEntity(app_v1);

        Assert.assertNotNull(server);
        assertEquals(new Long(12l), server.getId());
        assertEquals("gamexserver", server.getHostname());

        List<Application> apps  = server.getApps();
        assertNull(apps);
    }

    @Test
    public void testContentToEntity_update() throws IOException {

        String server_v1 = TestUtils.getTestResourceAsString("server_v1.json");
        System.out.println("Content: "+ server_v1);
        Assert.assertNotNull(server_v1);

        Server server = new ServerV1Transformer().contentToEntity(13l, server_v1);

        Assert.assertNotNull(server);
        assertEquals(new Long(13l), server.getId());
        assertEquals("gamexserver", server.getHostname());

    }
}
