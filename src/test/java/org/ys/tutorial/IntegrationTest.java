/*
 * Copyright (c) 2019. Yousif S
 */

package org.ys.tutorial;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.ServerSocket;

import static org.junit.Assert.assertTrue;

public abstract class IntegrationTest {


    private HttpServer httpServer;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // get free port
        ServerSocket s = new ServerSocket(0);


        // start the server
        AppServer appServer = new AppServer();
        httpServer = appServer.start();
        // create the client
        Client c = ClientBuilder.newClient();
        target = c.target(appServer.getURI());
    }

    @After
    public void tearDown() throws Exception {
        httpServer.shutdownNow();
    }

    Response get(String path) {
        return target.path(path).request().get();
    }

    Response post(String path, Object body){
        Invocation.Builder invocationBuilder =  target.path(path).request(MediaType.APPLICATION_JSON);
        return invocationBuilder.post(Entity.entity(body, MediaType.APPLICATION_JSON));
    }

    void assertSuccess(Response response) {
        int status  = response.getStatus();
        assertTrue("status "+status+" is not a success status code", status >=200 && status <300);
    }



}
