package com.oracle.kafkaui.repository.exception;

import javax.ws.rs.core.Response;

public class DeploymentNotFound extends RuntimeException {
    public Response toResponse() {
        return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
    }
}
