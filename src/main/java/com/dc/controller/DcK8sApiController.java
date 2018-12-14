package com.dc.controller;

import com.dc.config.K8sConfigComponent;
import com.dc.service.DcK8sApiService;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.extensions.DeploymentList;
import io.fabric8.kubernetes.api.model.extensions.ReplicaSetList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

@RestController
@RequestMapping(value = "/test")
public class DcK8sApiController {
    private DcK8sApiService dcK8sApiService;

    @Autowired
    K8sConfigComponent k8sConfigComponent;

    @ApiOperation(value = "Init", notes = "Init")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String initK8s() {
        String apiVersion = k8sConfigComponent.getApiVersion();
        String master = k8sConfigComponent.getMasterUrl();
        String caCertData = k8sConfigComponent.getCaCertData();
        String clientCertData = k8sConfigComponent.getClientCertData();
        String clientKeyData = k8sConfigComponent.getClientKeyData();
        return dcK8sApiService.init(master, apiVersion, caCertData, clientCertData, clientKeyData);
    }

    // Kubernetes namespace list
    @ApiOperation(value = "ListNamespace", notes = "ListNamespace")
    @RequestMapping(value = "/listnamespace", method = RequestMethod.GET)
    public NamespaceList listK8sNamespace() {
        return dcK8sApiService.listNamespace();
    }

    // Kubernetes node list
    @ApiOperation(value = "ListNode", notes = "ListNode")
    @RequestMapping(value = "/listnode", method = RequestMethod.GET)
    public NodeList listK8sNode() {
        return dcK8sApiService.listNode();
    }

    //list all storage classes
    @ApiOperation(value = "ListAllStorageClasses", notes = "List All Storage Classes")
    @RequestMapping(value = "/listallstorageclasses", method = RequestMethod.GET)
    public StorageClassList listAllStorageClasses() {
        return dcK8sApiService.listAllStorageClasses();
    }

    // Kubernetes pod list
    @ApiOperation(value = "ListPod",
                  notes = "ListPod",
                  httpMethod = "GET")
    @RequestMapping(value = "/listpod/{namespace}", method = RequestMethod.GET)
    @ResponseBody
    public PodList listK8sPodByNamespace(@PathVariable(value = "namespace") String namespace) {
        return dcK8sApiService.listK8sPodByNamespace(namespace);
    }

    // Kubernetes pod detail
    @ApiOperation(value = "Detail Pod", notes = "Detail Pod")
    @RequestMapping(value = "/poddetail/{namespace}/{name}", method = RequestMethod.GET)
    public Pod detailPod(@PathVariable(value = "namespace") String namespace,
                         @PathVariable(value = "name") String name) {
        return dcK8sApiService.detailPod(namespace, name);
    }

    // Kubernetes service list
    @ApiOperation(value = "ListService", notes = "List Service")
    @RequestMapping(value = "/listservice/{namespace}", method = RequestMethod.GET)
    public ServiceList listServiceByNamespace(@PathVariable(value = "namespace") String namespace) {
        return dcK8sApiService.listServiceByNamespace(namespace);
    }

    @ApiOperation(value = "DetailService", notes = "Detail Service")
    @RequestMapping(value = "/servicedetail/{namespace}/{name}", method = RequestMethod.GET)
    public Service detailService(@PathVariable(value = "namespace") String namespace,
                                 @PathVariable(value = "name") String name) {
        return dcK8sApiService.detailService(namespace, name);
    }

    // Kubernetes deployment list
    @ApiOperation(value = "ListDeployment", notes = "List Deployment")
    @RequestMapping(value = "/listdeployment/{namespace}", method = RequestMethod.GET)
    public DeploymentList listDeploymentByNamespace(@PathVariable(value = "namespace") String namespace) {
        return dcK8sApiService.listDeploymentByNamespace(namespace);
    }

    // Kubernetes replicationcontroller list
    @ApiOperation(value = "ListReplicationController", notes = "List Replication Controller")
    @RequestMapping(value = "/listreplicationcontroller/{namespace}", method = RequestMethod.GET)
    public ReplicationControllerList listReplicationControllerByNamespace(
            @PathVariable(value = "namespace") String namespace) {
        return dcK8sApiService.listReplicationControllerByNamespace(namespace);
    }

    // Kubernetes replicaset list
    @ApiOperation(value = "ListReplicaSet", notes = "List Replica Set")
    @RequestMapping(value = "/listreplicaset/{namespace}", method = RequestMethod.GET)
    public ReplicaSetList listReplicaSetByNamespace(
            @PathVariable(value = "namespace") String namespace) {
        return dcK8sApiService.listReplicaSetByNamespace(namespace);
    }
}
