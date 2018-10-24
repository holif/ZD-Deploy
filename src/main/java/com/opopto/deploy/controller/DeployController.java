package com.opopto.deploy.controller;

import com.opopto.deploy.model.App;
import com.opopto.deploy.model.Log;
import com.opopto.deploy.model.Node;
import com.opopto.deploy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.expression.spel.ast.BooleanLiteral;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@ResponseBody
public class DeployController extends ExceptionController {

    @Autowired
    private AppService appService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private AppNodeService appNodeService;
    @Autowired
    private DeployService deployService;
    @Autowired
    private LogService logService;
    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/deploy/action", method = RequestMethod.POST)
    public String deploy(@RequestBody String filePath, @RequestBody String comment){
        String[] temps = filePath.split("/");
        String fileName = temps[temps.length-1];
        List<Node> nodeList = new ArrayList<Node>();

        App app = appService.oneByFileName(fileName);

        appNodeService.listByAppId(app.getId()).stream().forEach(appNode -> {
            Node node = nodeService.one(appNode.getNodeId());
            nodeList.add(node);
        });

        boolean res = deployService.deploy(filePath,app,nodeList);
        mailService.sendHtmlMail("应用部署通知","<h3>部署应用：</h3><br>" + app.getProject() + "<br>" + app.getServiceName());
        Log log = new Log();
        log.setTime(new Date());
        log.setServiceName(app.getServiceName());
        log.setServiceVersion(temps[temps.length-2]);
        log.setIsSuccess(res ? 0 : 1);
        log.setComment(comment);
        log.setOperatorName("");
        logService.save(log);

        return "ok";
    }

    @RequestMapping("/file")
    public List<File> listFile(@RequestBody String path){
        return deployService.filelist(path);
    }
}
