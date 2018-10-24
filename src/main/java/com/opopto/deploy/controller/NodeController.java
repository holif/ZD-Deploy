package com.opopto.deploy.controller;

import com.opopto.deploy.model.Node;
import com.opopto.deploy.service.AppNodeService;
import com.opopto.deploy.service.MailService;
import com.opopto.deploy.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@ResponseBody
@RequestMapping("/node")
public class NodeController extends ExceptionController {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private AppNodeService appNodeService;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Node create(@RequestBody Node node) {

        Node nnode = nodeService.save(node);
        mailService.sendHtmlMail("添加节点通知","<h3>添加了节点：</h3><br>" + node.toString());
        return nnode;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Node node){
        Node oldNode = nodeService.one(node.getId());
        nodeService.update(node);
        node = nodeService.one(node.getId());
        mailService.sendHtmlMail("修改节点通知","<h3>节点修改前：</h3><br>" + oldNode.toString()+"<br><h3>节点修改后：</h3><br>" + node.toString());
        return "ok";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestBody Integer id) {
        if(!appNodeService.listByNodeId(id).isEmpty()){
            return "此节点上仍有服务部署，请先解除再尝试！";
        }
        Node oldNode = nodeService.one(id);
        nodeService.delete(id);
        mailService.sendHtmlMail("删除节点通知","<h3>删除的节点信息：</h3><br>" + oldNode.toString());
        return "ok";
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Node get(@PathVariable(value = "id") Integer id) {
        return nodeService.one(id);
    }

    @RequestMapping("/list")
    public Collection<Node> list() {
        return nodeService.list();
    }

}
