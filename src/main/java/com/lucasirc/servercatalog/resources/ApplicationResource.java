package com.lucasirc.servercatalog.resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.model.Server;
import com.lucasirc.servercatalog.service.ApplicationResourceService;
import com.lucasirc.servercatalog.service.ResourceFactory;

import org.apache.log4j.Logger;

import javax.ws.rs.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("{version}/applications")
public class ApplicationResource {

    public static final Logger log = Logger.getLogger(ApplicationResource.class);

    @PathParam("version")
    String version;
    @Context
    UriInfo uriInfo;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") long id) {
        log.debug("App find: " + id);
        Map appMap = getAppResourceService().get(id);

        if (appMap == null) {
            return notFound(id);
        }

        String json = new Gson().toJson(appMap);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(String content) {
        try {
            Application app = getAppResourceService().create(content);

            URI uri = URI.create(uriInfo.getRequestUri() + "/" +app.getId());
            return Response.created(uri).build();
        } catch (Exception e) {
            return responseError("Erro ao criar Aplicação, content: " + content, e);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(String content, @PathParam("id") long id) {
        try {
            Application app = getAppResourceService().update(id, content);

            if (app == null ){
                return notFound(id);
            }

            return Response.ok().build();
        } catch (Exception e ) {
            return responseError("Erro ao atualizar servidor, content: " + content, e);
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

    public Response notFound(long id){
        JsonObject json = new JsonObject();
        json.addProperty("msg", id + " not found!");

        return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
    }

    public Response responseError(String msg, Throwable e) {
        log.error(msg, e);
        JsonObject json = new JsonObject();
        json.addProperty("msg", msg);
        return Response.serverError().entity(json.toString()).build();
    }
}
