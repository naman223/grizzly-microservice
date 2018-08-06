package com.oracle.kafkaui.service;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainer;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "0.0.0.0";
    public static int PORT = 8090;

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        final HttpServer server = new HttpServer();
        final NetworkListener listener = new NetworkListener("grizzly", BASE_URI, PORT);

        server.addListener(listener);

        final ServerConfiguration config = server.getServerConfiguration();
        // add handler for serving static content
        config.addHttpHandler(new StaticHttpHandler("src/webapp"), "/");

        // add handler for serving JAX-RS resources
        config.addHttpHandler(RuntimeDelegate.getInstance()
                        .createEndpoint(new ResourceConfig().packages("com.oracle.kafkaui"),
                                GrizzlyHttpContainer.class),"/v1");

        try {
            // Start the server.
            server.start();
        } catch (Exception ex) {
            throw new ProcessingException("Exception thrown when trying to start grizzly server", ex);
        }

        // register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                server.shutdown();
            }
        }, "shutdownHook"));

        return server;
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final HttpServer server = startServer();
        System.out.println(String.format("Kafka UI app Started @ %s having Status flag @ %s", BASE_URI, server.isStarted()));
        System.out.println("Press CTRL^C to exit..");
        Thread.currentThread().join();
    }
}

