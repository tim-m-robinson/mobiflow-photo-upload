package org.tmr.rest;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.cdi.ContextName;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Created by tmr
 */
@ManagedBean
@RequestScoped
@Path("/")
public class PhotoUploadService {

    @Inject
    @ContextName("MobiflowCamel")
    CamelContext camelCtx;

    @GET
    @Path("/ping")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ping() {
        return Response.ok(Json.createObjectBuilder().add("result","OK").build().toString()).build();
    }

    @POST
    @Path("/uploadimage")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(@MultipartForm PhotoUploadForm form) {
        try {

            System.out.println("****** DEBUG ******");
            System.out.println("REST result: \n"+ form.toString());

            // Use the injected Camel Context to send the Java Form Object
            // into the 'direct' route endpoint
            ProducerTemplate producer = camelCtx.createProducerTemplate();
            PhotoUploadForm responseForm = producer.requestBody("direct:echo", form, PhotoUploadForm.class);

            // Verify that Camel has processed the Java form
            System.out.println("Camel result: \n"+ responseForm.toString());

            JsonObject jsonResponse = Json.createObjectBuilder()
                                            .add("code", Response.Status.ACCEPTED.toString())
                                            .add("type", "success")
                                            .add("message", "image uploaded, size "+form.getDocumentImage().length+" bytes")
                                            .build();

            return Response.status(Response.Status.ACCEPTED).entity(jsonResponse.toString()).build();

        } catch (Exception e) {
            e.printStackTrace();

            JsonObject errResponse = Json.createObjectBuilder()
                    .add("code", Response.Status.INTERNAL_SERVER_ERROR.toString())
                    .add("type", "error")
                    .add("message", ""+e.toString())
                    .build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errResponse.toString()).build();
         }
    }

    // A test method for debugging CDI
    @PostConstruct
    public void init() {
        System.out.println("********************************");
        System.out.println("********************************");
        System.out.println("********************************");
        String ctxName = "none";
        if (camelCtx != null) ctxName = camelCtx.getName();
        System.out.println("Camel Context Name: "+ctxName);
    }

}
