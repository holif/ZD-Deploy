package com.opopto.deploy.jpa;

import com.opopto.deploy.model.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppRepository extends JpaRepository<App, Integer> {

    App findAppByFileName(String fileName);

    List<App> findOneByProjectAndFileName(String project, String fileName);

    @Modifying
    @Query(value = "update app set project=?1, file_name=?2, service_name=?3, deploy_path=?4 where id=?5", nativeQuery = true)
    Integer updateById(String project, String fileName, String serviceName, String deployPath, Integer id);
}
