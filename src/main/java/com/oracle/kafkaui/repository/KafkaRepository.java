package com.oracle.kafkaui.repository;

import com.oracle.kafkaui.model.Deployment;

import java.util.List;

public interface KafkaRepository {

    public List<Deployment> getAllDeployments();

    public Deployment getDeployment(int id);

    public void deleteDeployment(int id);

    public void addDeployment(Deployment deployment);
}
