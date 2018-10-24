package com.opopto.deploy.controller;

import com.opopto.deploy.model.App;
import com.opopto.deploy.model.AppNode;
import com.opopto.deploy.model.Log;
import com.opopto.deploy.model.Version;
import com.opopto.deploy.service.*;
import com.opopto.deploy.util.ExecuterCommand;
import com.opopto.deploy.util.HostUtil;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@CommonsLog
@ResponseBody
public class AppController extends ExceptionController {

    @Autowired
    private AppService appService;
    @Autowired
    private AppNodeService appNodeService;
    @Autowired
    private MailService mailService;
    @Autowired
    private AppLocationService appLocationService;
    @Autowired
    private LogService logService;

    @Value("${deploy.build_repo}")
    private String buildRepo;

    @RequestMapping(value = "/app/create", method = RequestMethod.POST)
    public App create(@RequestBody App app) {
        App napp = appService.save(app);
        mailService.sendHtmlMail("添加应用通知","<h3>添加的应用：</h3><br>" + napp.toString());
        return napp;
    }

    @RequestMapping(value = "/app/update", method = RequestMethod.POST)
    public String update(@RequestBody App app){
        App oldApp = appService.one(app.getId());
        appService.update(app);
        app = appService.one(app.getId());
        mailService.sendHtmlMail("修改应用通知","<h3>应用修改前：</h3><br>" + oldApp.toString()+"<br><h3>应用修改后：</h3><br>" + app.toString());
        return "ok";
    }

    @RequestMapping(value = "/app/get/{id}", method = RequestMethod.GET)
    public App get(@PathVariable(value = "id") Integer id) {
        return appService.one(id);
    }

    @GetMapping("/app/list")
    public List<App> list() {
        return appService.list();
    }

    @RequestMapping(value = "/app/names", method = RequestMethod.GET)
    public Collection<String> names(){
        Path path = Paths.get(buildRepo);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new RuntimeException("The build repo [" + buildRepo + "] has not exists or not directory!");
        }
        try {
            List<String> projects = Files.list(path)
                    .filter(Files::isDirectory)
                    .map(e -> e.getFileName().toString())
                    .sorted()
                    .collect(Collectors.toList());

            return projects;
        }catch (Exception e){
            return null;
        }
    }

    @RequestMapping(value = "/app/{project}", method = RequestMethod.GET)
    public Collection<Version> get(
            @PathVariable(value = "project") String project,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "9") Integer size) throws IOException {

        Collection<Version> versions = appLocationService.findVersions(project, page, size);

        return versions;
    }

    @RequestMapping(value = "/app/{project}/{version}", method = RequestMethod.GET)
    public Object get(
            @PathVariable(value = "project") String project,
            @PathVariable(value = "version") String version) throws IOException {
        appLocationService.appDeploy(project, version);
        App app = new App();
        app.setFileName( project +".jar");
        app.setDeployPath("/var/lib/"+project);
        app.setProject(project);
        app.setVersion(version);
        app.setServiceName(project);
        app.setUser(project);
        Collection<App> apps = Arrays.asList(app);
        return apps;
    }

    @RequestMapping(value = "/app/{project}/{version}/{fileName:.+}",  method = RequestMethod.POST)
    public Map<String, Object> post(
            @PathVariable(value = "project") String project,
            @PathVariable(value = "version") String version,
            @PathVariable(value = "fileName") String fileName,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "desc", required = false) String desc,
            HttpServletRequest request) {
        Map<String, Object> m = new HashMap<String, Object>();
        App app = appService.oneByFileName(fileName);
        if(app==null){
            m.put("result", false);
            return m;
        }
        log.info(app);

        String localPath = buildRepo+"/"+project + "/" +version + "/" +fileName;
        log.info(String.format("localPath: %s",localPath));

        List<String> hosts  = appNodeService.listByAppId(app.getId()).stream().map(appNode -> appNode.getHostName()).collect(Collectors.toList());
        log.info(String.format("service %s with hosts %s", app.getServiceName(),hosts));
        if(hosts.isEmpty()){
            return m;
        }

        Log deployLog = new Log();
        deployLog.setIsSuccess(0);
        deployLog.setComment(desc);
        deployLog.setOperatorName(username);
        deployLog.setServiceName(app.getServiceName());
        deployLog.setServiceVersion(version);
        deployLog.setTime(new Date());
        deployLog.setLoginIp(HostUtil.getIPAddress(request));
        logService.save(deployLog);

        ExecuterCommand executer = new ExecuterCommand();
        for (String host : hosts){
            if(StringUtils.isEmpty(host)){
                continue;
            }
            executer.rsync(localPath,host,app.getDeployPath());
            executer.restart(app.getServiceName(), host);
            log.info(String.format("deploy %s to %s success", app.getServiceName(), host));
        }
        deployLog.setIsSuccess(1);
        logService.save(deployLog);


        m.put("result", true);
        return m;
    }


}
