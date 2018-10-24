package com.opopto.deploy.service;

import com.opopto.deploy.jpa.NodeRepository;
import com.opopto.deploy.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeService {

    @Autowired
    private NodeRepository nodeRepository;

    public Node save(Node node) {
        return nodeRepository.save(node);
    }

    public void update(Node node) {
        nodeRepository.updateHostNameAndHostById(node.getHostName(), node.getHost(), node.getId());
    }

    public void delete(Integer id) {
        nodeRepository.delete(id);
    }

    public Node one(Integer id) {
        return nodeRepository.findOne(id);
    }

    public List<Node> list() {
        return nodeRepository.findAll();
    }

}
