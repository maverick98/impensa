/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.impensa;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;

/**
 * REST Web Service
 *
 * @author manosahu
 */
public class HelloResource {

    private String hello;

    /**
     * Creates a new instance of HelloResource
     */
    private HelloResource(String hello) {
        this.hello = hello;
    }

    /**
     * Get instance of the HelloResource
     */
    public static HelloResource getInstance(String hello) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of HelloResource class.
        return new HelloResource(hello);
    }

    /**
     * Retrieves representation of an instance of org.impensa.HelloResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of HelloResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource HelloResource
     */
    @DELETE
    public void delete() {
    }
}
