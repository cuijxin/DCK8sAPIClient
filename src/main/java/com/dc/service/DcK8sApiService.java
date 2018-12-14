package com.dc.service;

import ch.qos.logback.core.util.TimeUtil;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.api.model.extensions.DeploymentList;
import io.fabric8.kubernetes.api.model.extensions.ReplicaSetList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import sun.reflect.annotation.ExceptionProxy;

import java.util.concurrent.TimeUnit;

public class DcK8sApiService {

    private static KubernetesClient kubernetesClient;
    private static Config config;

    // 初始化 - 连接Kubernetes api server
    public static String init(String master, String apiVersion, String caCertData,
            String clientCertData, String clientKeyData) {
        String initResult = "Init Failed.";
        try {
            config = new ConfigBuilder()
                    .withMasterUrl(master)
                    .withApiVersion(apiVersion)
                    .withCaCertData(caCertData)
                    .withClientCertData(clientCertData)
                    .withClientKeyData(clientKeyData)
                    .build();
            kubernetesClient = new DefaultKubernetesClient(config);

            initResult = "Init Success.";
            System.out.println("init success");
        } catch (Exception e) {
            System.out.println("can't init discovery service");
        }
        return initResult;
    }

    // 列出当前命名空间
    public static NamespaceList listNamespace() {
        NamespaceList namespaceList = new NamespaceList();
        try {
            namespaceList = kubernetesClient.namespaces().list();
            System.out.println("list success");
        } catch (Exception e) {
            System.out.println("list failed");
        }
        return namespaceList;
    }

    // 列出当前可用节点
    public static NodeList listNode() {
        NodeList nodeList = new NodeList();
        try {
            nodeList = kubernetesClient.nodes().list();
            System.out.println("list success");
        } catch (Exception e) {
            System.out.println("list failed");
        }
        return nodeList;
    }

    //list all storage classes
    public static StorageClassList listAllStorageClasses() {
        StorageClassList storageClassList = new StorageClassList();
        try {
            storageClassList = kubernetesClient.storageClasses().list();
        } catch (Exception e) {
            System.out.println("list failed");
        }
        return storageClassList;
    }

    // 列出指定命名空间下的pod
    public static PodList listK8sPodByNamespace(String namespace) {
        PodList podList = new PodList();
        try {
            podList = kubernetesClient.pods().inNamespace(namespace).list();
        } catch (Exception e) {
            System.out.println("list failed");
        }
        return podList;
    }

    public static Pod detailPod(String namespace, String name) {
        Pod pod = new Pod();
        try {
//            pod = kubernetesClient.pods().inNamespace(namespace)
//                    .withName(name).waitUntilReady(5, TimeUnit.SECONDS);
            pod = kubernetesClient.pods().inNamespace(namespace).withName(name).get();
        } catch (Exception e) {
            System.out.println("get pod detail failed");
        }

        return pod;
    }

    // 列出指定命名空间下的service
    public static ServiceList listServiceByNamespace(String namespace) {
        ServiceList serviceList = new ServiceList();

        try {
            serviceList = kubernetesClient.services().inNamespace(namespace).list();
        } catch (Exception e) {
            System.out.println("list failed");
        }
        return serviceList;
    }

    public static Service detailService(String namespace, String name) {
        Service service = new Service();
        try {
//            service = kubernetesClient.services().inNamespace(namespace)
//                    .withName(name).waitUntilReady(5, TimeUnit.SECONDS);
            service = kubernetesClient.services().inNamespace(namespace)
                    .withName(name).get();
        } catch (Exception e) {
            System.out.println("detail service failed");
        }
        return service;
    }

    // 列出指定命名空间下的deployment
    public static DeploymentList listDeploymentByNamespace(String namespace) {
        DeploymentList deploymentList = new DeploymentList();

        try {
            deploymentList = kubernetesClient.extensions().deployments().inNamespace(namespace).list();
        } catch (Exception e) {
            System.out.println("list failed");
        }
        return deploymentList;
    }

    public static Deployment detailDeployment(String namespace, String name) {
        Deployment deployment = new Deployment();
        try {
            deployment = kubernetesClient.extensions().deployments().inNamespace(namespace)
                    .withName(name).get();
        } catch (Exception e) {
            System.out.println("detail deployment failed");
        }
        return deployment;
    }

    // 列出指定命名空间下的replicationcontroller
    public static ReplicationControllerList listReplicationControllerByNamespace(String namespace) {
        ReplicationControllerList replicationControllerList = new ReplicationControllerList();

        try {
            replicationControllerList = kubernetesClient.replicationControllers().inNamespace(namespace).list();
        } catch (Exception e) {
            System.out.println("list failed");
        }
        return replicationControllerList;
    }

    // 列出指定命名空间下的replicaset
    public static ReplicaSetList listReplicaSetByNamespace(String namespace) {
        ReplicaSetList replicaSetList = new ReplicaSetList();

        try {
            replicaSetList = kubernetesClient.extensions().replicaSets().inNamespace(namespace).list();
        } catch (Exception e) {
            System.out.println("list failed");
        }
        return replicaSetList;
    }
}
