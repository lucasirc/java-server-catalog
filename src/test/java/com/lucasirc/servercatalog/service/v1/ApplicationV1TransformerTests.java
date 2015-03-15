package com.lucasirc.servercatalog.service.v1;

import com.lucasirc.servercatalog.model.Application;

import com.lucasirc.servercatalog.util.TestUtils;
import org.glassfish.jersey.message.internal.XmlCollectionJaxbProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

public class ApplicationV1TransformerTests {

    @Test
    public void testEntityToMap() {
        Application app = new Application(14l, "Game", "jose");

        Map map = new ApplicationV1Transformer().entityToMap(app);

        assertEquals(map.get("id"), 14l);
        assertEquals(map.get("name"), "Game");
        assertEquals(map.get("owner"), "jose");
    }
    @Test
    public void testContentToEntity() throws IOException {

        String app_v1 = TestUtils.getTestResourceAsString("application_v1.json");
        System.out.println("Content: "+ app_v1);
        Assert.assertNotNull(app_v1);

        Application app = new ApplicationV1Transformer().contentToEntity(app_v1);

        Assert.assertNotNull(app);
        assertEquals("lucas", app.getName());
        assertEquals("lucasirc@gmail.com", app.getOwner());
    }
    @Test
    public void testContentToEntity_update() throws IOException {

        String app_v1 = TestUtils.getTestResourceAsString("application_v1.json");
        System.out.println("Content: "+ app_v1);
        Assert.assertNotNull(app_v1);

        Application app = new ApplicationV1Transformer().contentToEntity(13l, app_v1);

        Assert.assertNotNull(app);
        assertEquals(new Long(13l), app.getId());
        assertEquals("lucas", app.getName());
        assertEquals("lucasirc@gmail.com", app.getOwner());
    }
}
