package com.opopto.deploy.service;

import com.opopto.deploy.model.App;
import com.opopto.deploy.model.Node;
import com.opopto.deploy.util.ExecuterCommand;
import com.opopto.deploy.util.FileUtil;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@CommonsLog
public class DeployService {

    @Autowired
    private ExecuterCommand executerCommand;

    @Value("${deploy.build_repo}")
    private String build_repo;

    public List<File> filelist(String path){
        if (path.isEmpty()) {
            return FileUtil.getFile(new File(build_repo));
        } else {
            return FileUtil.getFile(new File(build_repo + path));
        }
    }

    public boolean deploy(String filePath ,App app, List<Node> nodes){

        String localPath = build_repo + filePath;

        nodes.stream().forEach(node -> {
            executerCommand.rsync(localPath, node.getHost(), app.getDeployPath());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                log.warn("Thread sleep exception...");
            }
            executerCommand.restart(app.getServiceName(), node.getHost());
        });

        log.info("Deploy finish");

        return true;
    }

}
