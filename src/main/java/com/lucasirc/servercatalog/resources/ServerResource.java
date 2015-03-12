package com.lucasirc.servercatalog.resources;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lucasirc.servercatalog.model.Server;
import com.lucasirc.servercatalog.service.ServerResourceService;
import com.lucasirc.servercatalog.service.ResourceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("{version}/servers")
public class ServerResource {

    @PathParam("version")
    String version;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("offset") long offset,
                         @QueryParam("max") long max) {

        offset = offset != 0 ? offset : 0;
        max = Math.min(max != 0 ? max : 10, 100);

        List list = getResourceService().list(offset, max);

        String json = new Gson().toJson(list);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") long id) {
        Map map = getResourceService().get(id);

        if(map == null) {
            JsonObject json = new JsonObject();
            json.addProperty("msg", id + " not found!");

            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }
        String json = new Gson().toJson(map);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(String content) {
        Server server = getResourceService().create(content);

        URI uri = URI.create("/"+version+"/servers/" +server.getId());
        return Response.created(uri).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id){

        ServerResourceService service = getResourceService();

        service.delete(id);

        return Response.ok().build();
    }

    public ServerResourceService getResourceService() {
        return ResourceFactory.getServerService(version);
    }

}

