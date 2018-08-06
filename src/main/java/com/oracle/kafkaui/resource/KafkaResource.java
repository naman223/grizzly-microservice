package com.oracle.kafkaui.resource;

import com.oracle.kafkaui.model.Deployment;
import com.oracle.kafkaui.repository.KafkaRepository;
import com.oracle.kafkaui.repository.KafkaRepositoryImpl;
import com.oracle.kafkaui.repository.exception.DeploymentNotFound;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class KafkaResource {

    private KafkaRepository kafkaRepository = KafkaRepositoryImpl.getInstance();

    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public Response getAllDeployments() {
        try {
            List<Deployment> deploymentList =  kafkaRepository.getAllDeployments();
            GenericEntity<List<Deployment>> list = new GenericEntity<List<Deployment>>(deploymentList) {
            };
            return Response.ok(list).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Response getDeployment(@PathParam("id") int id) {
        Deployment deployment;
        try {
            deployment = kafkaRepository.getDeployment(id);
            return Response.ok(deployment).build();
        } catch (DeploymentNotFound e) {
            return e.toResponse();
        } catch (Exception e) {
            return internalError();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response deleteDeployment(@PathParam("id") int id) {
        kafkaRepository.deleteDeployment(id);
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response addEmployee(Deployment deployment) {
        try {
            // Run external code or execute script get the response as below
            kafkaRepository.addDeployment(new Deployment(deployment.getId(), deployment.getDeploymentName(), deployment.getDeploymentEndPoint(), deployment.getStatus()));
            return Response.status(Response.Status.CREATED.getStatusCode()).entity(deployment).build();
        } catch (Exception e) {
            return internalError();
        }
    }

    private Response internalError() {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).build();
    }
}
