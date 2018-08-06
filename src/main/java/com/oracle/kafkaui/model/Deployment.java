package com.oracle.kafkaui.model;

public class Deployment {

    private String deploymentName;
    private String status;
    private String deploymentEndPoint;
    private int id;

    public Deployment() {
    }

    public Deployment(int id, String deploymentName, String deploymentEndPoint, String status) {
        this.id = id;
        this.deploymentName = deploymentName;
        this.deploymentEndPoint = deploymentEndPoint;
        this.status = status;
    }


    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeploymentEndPoint() {
        return deploymentEndPoint;
    }

    public void setDeploymentEndPoint(String deploymentEndPoint) {
        this.deploymentEndPoint = deploymentEndPoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
