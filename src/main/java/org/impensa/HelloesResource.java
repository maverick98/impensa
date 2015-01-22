/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.impensa;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author manosahu
 */
@Path("/api/v1/data/")
public class HelloesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HelloesResource
     */
    public HelloesResource() {
    }

    /**
     * Retrieves representation of an instance of org.impensa.HelloesResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public HelloWorld getJson() {
        //TODO return proper representation object
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setId("1");
        helloWorld.setName("Reetika");
        return helloWorld;
    }

    /**
     * POST method for creating an instance of HelloResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response postJson(String content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {hello}
     */
    @Path("{hello}")
    public HelloResource getHelloResource(@PathParam("hello") String hello) {
        return HelloResource.getInstance(hello);
    }
}
