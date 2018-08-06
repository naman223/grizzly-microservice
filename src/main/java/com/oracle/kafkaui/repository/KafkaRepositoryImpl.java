package com.oracle.kafkaui.repository;

import com.oracle.kafkaui.model.Deployment;
import com.oracle.kafkaui.repository.exception.DeploymentNotFound;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class KafkaRepositoryImpl implements KafkaRepository {

    private static KafkaRepository deploymentRepository;
    private List<Deployment> deploymentList;

    private KafkaRepositoryImpl() {
    }

    public static KafkaRepository getInstance() {
        if(deploymentRepository ==null)
            deploymentRepository = new KafkaRepositoryImpl();

        return deploymentRepository;
    }


    public List<Deployment> getAllDeployments() {
        return deploymentList;
    }

    public Deployment getDeployment(int id) {
        for (Deployment deployment : deploymentList) {
            if (deployment.getId() == id) {
                return deployment;
            }
        }
        throw new DeploymentNotFound();
    }

    public void deleteDeployment(int id) {
        for (Deployment deployment : deploymentList) {
            if (deployment.getId() == id) {
                deploymentList.remove(deployment);
                return;
            }
        }
        throw new DeploymentNotFound();
    }

    public void addDeployment(Deployment deployment) {
        deploymentList.add(deployment);
    }
}
