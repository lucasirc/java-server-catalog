package com.lucasirc.servercatalog.resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.model.Server;
import com.lucasirc.servercatalog.service.ApplicationResourceService;
import com.lucasirc.servercatalog.service.ResourceFactory;
import com.lucasirc.servercatalog.service.ServerResourceService;

import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
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


        if (appMap == null) {
            JsonObject json = new JsonObject();
            json.addProperty("msg", id + " not found!");

            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("offset") long offset,
                         @QueryParam("max") long max) {

        offset = offset != 0 ? offset : 0;
        max = Math.min(max != 0 ? max : 10, 100);

        List list = getAppResourceService().list(offset, max);

        String json = new Gson().toJson(list);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }


    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id){
        ApplicationResourceService service = getAppResourceService();

        service.delete(id);

        return Response.ok().build();
    }

    public ApplicationResourceService getAppResourceService() {
        return ResourceFactory.getAppResourceService(version);
    }
}
