package com.lucasirc.servercatalog.service.v1;

import com.lucasirc.servercatalog.model.Application;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.Map;

public class ApplicationV1TransformerTests {

    @Test
    public void testEntityToMap() {
        Application app = new Application(1l, "Game", "jose");

        Map map = new ApplicationV1Transformer().entityToMap(app);

        assertEquals(map.get("id"), 1l);
        assertEquals(map.get("name"), "Game");
        assertEquals(map.get("owner"), "jose");
    }

}
