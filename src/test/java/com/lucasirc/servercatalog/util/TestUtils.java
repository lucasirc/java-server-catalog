package com.lucasirc.servercatalog.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {

    /**
     * retorna conteudo de um arquivo dentro da pasta resources de test
     *
     */
    public static String getTestResourceAsString(String filePath) throws IOException {
        java.io.File file = new java.io.File("");   //Dummy file
        String path = file.getAbsolutePath() + "/src/test/resources/" + new File(filePath);
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, "UTF-8");
    }
}
