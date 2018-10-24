package com.opopto.deploy.util;

import lombok.extern.apachecommons.CommonsLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@CommonsLog
public class ExecuterCommand {

    public boolean restart(String service, String host) {
        exec("ssh", host, "supervisorctl", "reread");
        exec("ssh", host, "supervisorctl", "restart", service);
        return true;
    }

    public boolean rsync(String localPath, String host, String deployPath) {
        return exec("rsync", "-avz", localPath, host + ":" + deployPath);
    }

    public boolean exec(String... elements) {
        String command = String.join(" ", elements);

        log.info("Executer command with : " + command);

        int exitValue = -1;
        try {
            Process process = Runtime.getRuntime().exec(command);
            exitValue = process.waitFor();
        } catch (Exception e) {
            log.error("command executer fail");
            throw new RuntimeException("command executer fail");
        }
        return exitValue == 0 ? true : false;
    }
}
