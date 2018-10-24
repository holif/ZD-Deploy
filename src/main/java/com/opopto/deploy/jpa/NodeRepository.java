package com.opopto.deploy.jpa;

import com.opopto.deploy.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NodeRepository extends JpaRepository<Node, Integer> {

    @Modifying
    @Query(value = "update node set host_name=?1, host=?2 where id=?3",nativeQuery = true)
    Integer updateHostNameAndHostById(String hostName, String host, Integer id);

}
