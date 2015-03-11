package com.lucasirc.servercatalog.service;

import com.lucasirc.servercatalog.dao.ApplicationDAO;
import com.lucasirc.servercatalog.dao.DefaultDAO;
import com.lucasirc.servercatalog.model.Application;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationResourceServiceTests {

    @Test
    public void testDaoInstance() {
        DefaultDAO<Application> dao = new ApplicationResourceService(null).getDAO();
        Assert.assertTrue(dao instanceof ApplicationDAO);
    }
}
