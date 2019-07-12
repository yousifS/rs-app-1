/*
 * Copyright (c) 2019. Yousif S
 */

package org.ys.tutorial;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.util.logging.Level;

/**
 * Main class.
 *
 */
public class AppServer {

    private static Logger logger = LoggerFactory.getLogger(AppServer.class);

    /**
     * Main method to start the http server,
     *
     * @throws IOException
     */
    public static void main(String[] args) {
        // redirect java.util.Logger to SLF4J
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        java.util.logging.Logger.getGlobal().setLevel(Level.FINEST);

        AppServer server = new AppServer(8080);
        HttpServer httpServer = server.start();

        // block main thread
        try{
            Thread.currentThread().join();
        } catch (Throwable t){
            httpServer.shutdown();
        }
    }

    /**
     * port the http server is listening on
     */
    private int port;

    /**
     * @param port the port to listen to
     */
    AppServer(int port){
        this.port = port;
    }

    /**
     * A random port will be selected for the app server to listen to
     */
    AppServer(){
        this.port = -1;
    }

    /**
     * Starts the http server
     * @return the http server
     */
    HttpServer start() {
        if(port == -1){
            try{
                port = new ServerSocket(0).getLocalPort();
            } catch (IOException e) {
                logger.error("Failed to find an open port", e);
            }
        }

        // start the http server
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(
                "http://localhost:"+port),
                new AppConfig(),
                false,
                null,
                true);

        // add a shutdown hook for the http server
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping server..");
            server.shutdown();
        }));

        return server;

    }

    /**
     * @return the uri the http server is listening on
     */
    public URI getURI() {
        return URI.create("http://localhost:"+port);
    }
}

