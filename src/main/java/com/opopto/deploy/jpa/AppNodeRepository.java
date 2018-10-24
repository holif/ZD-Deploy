package com.opopto.deploy.jpa;

import com.opopto.deploy.model.AppNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppNodeRepository extends JpaRepository<AppNode, Integer> {

    List<AppNode> findAllByAppIdAndDelFlag(Integer appId, Integer delFlag);

    List<AppNode> findAllByNodeIdAndDelFlag(Integer nodeId, Integer delFlag);

    @Modifying
    @Query(value = "update app_node set app_id=?1 ,node_id=?2 where id=?3",nativeQuery = true)
    Integer updateAppIdAndNodeIdById(Integer appId, Integer nodeId, Integer id);

}
