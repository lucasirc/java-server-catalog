package com.lucasirc.servercatalog.resources;

import com.google.gson.Gson;
import com.lucasirc.servercatalog.service.ApplicationResourceService;
import com.lucasirc.servercatalog.service.ResourceFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("{version}/applications")
public class ApplicationResource {

    @PathParam("version")
    String version;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") long id) {
        System.out.println("Appls: " + id);
        Map appMap = getAppResourceService().get(id);

        String json = new Gson().toJson(appMap);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    public ApplicationResourceService getAppResourceService() {
        return ResourceFactory.getAppResourceService(version);
    }
}
