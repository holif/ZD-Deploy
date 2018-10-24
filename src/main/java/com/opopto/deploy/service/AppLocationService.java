package com.opopto.deploy.service;

import com.opopto.deploy.model.App;
import com.opopto.deploy.model.Version;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: jone
 * @date: Created in 2018/8/12 23:25
 * @description:
 */
@Service
@CommonsLog
public class AppLocationService {

    @Value("${deploy.build_repo}")
    private String buildRepo;

    private Pattern pattern = Pattern.compile(".*\\.(war|jar)$");


    public Collection<String> findProjects() throws IOException {
        Path path = Paths.get(buildRepo);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new RuntimeException("The build repo [" + buildRepo + "] has not exists or not directory!");
        }

        List<String> projects = Files.list(path)
                .filter(Files::isDirectory)
                .map(e -> e.getFileName().toString())
                .sorted()
                .collect(Collectors.toList());

        return projects;
    }

    public Collection<Version> findVersions(String project, Integer page, Integer size) throws IOException {
        Path path = Paths.get(buildRepo).resolve(project);

        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new RuntimeException("The project [" + project + "] has not exists!");
        }
        log.info("path " + path);
        List<Version> versions = Files.list(path)
                .filter(Files::isDirectory)
                .map(e -> Version.builder().version(e.getFileName().toString()).modifyTime(e.toFile().lastModified()).build())
                .filter(Objects::nonNull)
                //根据modify time逆序排列
                .sorted(Comparator.comparing(Version::getModifyTime, Comparator.reverseOrder()))
                .skip(size * (page - 1))
                .limit(size)
                .collect(Collectors.toList());

        return versions;
    }

    public void appDeploy(String project, String version) throws IOException {
        Path path = Paths.get(buildRepo).resolve(project).resolve(version);

        if (!Files.exists(path)) {
            throw new RuntimeException("The project's version [" + project + "::" + version + "] has not exists!");
        }

    }

}
