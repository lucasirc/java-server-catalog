package com.lucasirc.servercatalog.resources;

import com.google.gson.Gson;
import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.model.Server;
import com.lucasirc.servercatalog.service.ApplicationResourceService;
import com.lucasirc.servercatalog.service.ResourceFactory;

import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(String content) {
        try {
            Application app = getAppResourceService().create(content);

            URI uri = URI.create("/" + version + "/applications/" + app.getId());
            return Response.created(uri).build();
        } catch (Exception e) {
            e.printStackTrace();

            return Response.serverError().build();
        }
    }

    public ApplicationResourceService getAppResourceService() {
        return ResourceFactory.getAppResourceService(version);
    }
}
