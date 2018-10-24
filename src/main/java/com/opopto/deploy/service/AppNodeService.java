package com.opopto.deploy.service;

import com.opopto.deploy.jpa.AppNodeRepository;
import com.opopto.deploy.jpa.AppRepository;
import com.opopto.deploy.jpa.NodeRepository;
import com.opopto.deploy.model.App;
import com.opopto.deploy.model.AppNode;
import com.opopto.deploy.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppNodeService {

    @Autowired
    private AppNodeRepository appNodeRepository;
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private NodeRepository nodeRepository;

    public AppNode save(AppNode appNode){
        return appNodeRepository.save(appNode);
    }

    public AppNode one(Integer id){
        return appNodeRepository.findOne(id);
    }

    public void update(AppNode appNode) {
        appNodeRepository.updateAppIdAndNodeIdById(appNode.getAppId(), appNode.getNodeId(), appNode.getId());
    }

    public void delete(Integer id){
        appNodeRepository.delete(id);
    }

    public List<AppNode> listByAppId(Integer appId){
        List<AppNode> list = new ArrayList<>();
        appNodeRepository.findAllByAppIdAndDelFlag(appId,0).stream().forEach(appNode -> {
            App app = appRepository.findOne(appNode.getAppId());
            Node node = nodeRepository.findOne(appNode.getNodeId());
            AppNode temp = new AppNode();
            temp.setId(appNode.getId());
            temp.setAppId(app.getId());
            temp.setNodeId(node.getId());
            temp.setHost(node.getHost());
            temp.setHostName(node.getHostName());
            temp.setProject(app.getProject());
            temp.setFileName(app.getFileName());
            temp.setServiceName(app.getServiceName());
            temp.setDeployPath(app.getDeployPath());
            list.add(temp);
        });
        return list;
    }

    public List<AppNode> listByNodeId(Integer nodeId){
        return appNodeRepository.findAllByNodeIdAndDelFlag(nodeId,0);
    }

    public List<AppNode> list(){
        return appNodeRepository.findAll().stream().filter(appNode -> appNode.getDelFlag()!=1).collect(Collectors.toList());
    }

}
