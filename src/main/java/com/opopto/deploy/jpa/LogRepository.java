package com.opopto.deploy.jpa;

import com.opopto.deploy.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Integer> {

    List<Log> findAllByOperatorName(String operatorName);

    List<Log> findAllByServiceName(String serviceName);

}
