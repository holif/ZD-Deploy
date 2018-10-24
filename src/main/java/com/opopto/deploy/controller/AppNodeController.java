package com.opopto.deploy.controller;

import com.opopto.deploy.model.App;
import com.opopto.deploy.model.AppNode;
import com.opopto.deploy.model.Node;
import com.opopto.deploy.service.AppNodeService;
import com.opopto.deploy.service.AppService;
import com.opopto.deploy.service.MailService;
import com.opopto.deploy.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/appnode")
public class AppNodeController extends ExceptionController {

    @Autowired
    private AppNodeService appNodeService;
    @Autowired
    private AppService appService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public AppNode create(@RequestBody AppNode appNode) {
        AppNode nAppNode = appNodeService.save(appNode);
        mailService.sendHtmlMail("添加部署关系通知","<h3>添加的部署关系：</h3><br>" + nAppNode.toString());
        return nAppNode;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody AppNode appNode){
        AppNode oldAppNode = appNodeService.one(appNode.getId());
        appNodeService.update(appNode);
        appNode = appNodeService.one(appNode.getId());
        mailService.sendHtmlMail("修改部署关系通知","<h3>部署关系修改前：</h3><br>" + oldAppNode.toString()+"<br><h3>部署关系修改后：</h3><br>" + appNode.toString());
        return "ok";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestBody Integer id) {
        AppNode oldAppNode = appNodeService.one(id);
        appNodeService.delete(id);
        mailService.sendHtmlMail("删除部署关系通知","<h3>删除的部署关系信息：</h3><br>" + oldAppNode.toString());
        return "ok";
    }

    @RequestMapping("/get/{id}")
    public AppNode get(@PathVariable(value = "id") Integer id){
        AppNode appNode = appNodeService.one(id);
        App app = appService.one(appNode.getAppId());
        Node node = nodeService.one(appNode.getNodeId());

        appNode.setHost(node.getHost());
        appNode.setHostName(node.getHostName());
        appNode.setProject(app.getProject());
        appNode.setFileName(app.getFileName());
        appNode.setServiceName(app.getServiceName());
        appNode.setDeployPath(app.getDeployPath());

        return appNode;
    }

    @RequestMapping("/list")
    public Collection<AppNode> list() {
        List<AppNode> appNodeList = new ArrayList<AppNode>();
        List<AppNode> list = appNodeService.list();
        list.stream().forEach(appNode -> {
            App app = appService.one(appNode.getAppId());
            Node node = nodeService.one(appNode.getNodeId());
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
            appNodeList.add(temp);
        });

        return appNodeList;
    }

    @RequestMapping(value = "/load/app/{id}", method = RequestMethod.GET)
    public Collection<AppNode> listByAppId(@PathVariable(value = "id") Integer id) {
        List<AppNode> appNodeList = new ArrayList<AppNode>();
        App app = appService.one(id);

        appNodeService.listByAppId(id).stream().forEach(appNode -> {
            Node node = nodeService.one(appNode.getNodeId());
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
            appNodeList.add(temp);
        });
        return appNodeList;
    }

    @RequestMapping(value = "/load/node/{id}", method = RequestMethod.GET)
    public Collection<AppNode> listByNodeId(@PathVariable(value = "id") Integer id) {
        List<AppNode> appNodeList = new ArrayList<AppNode>();
        Node node = nodeService.one(id);

        appNodeService.listByNodeId(id).stream().forEach(appNode -> {
            App app = appService.one(appNode.getAppId());
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
            appNodeList.add(temp);
        });
        return appNodeList;
    }
}
