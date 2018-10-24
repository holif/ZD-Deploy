package com.opopto.deploy.service;

import com.opopto.deploy.jpa.LogRepository;
import com.opopto.deploy.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository repository;

    public Log save(Log log){
        return repository.save(log);
    }

    public List<Log> listByOperatorName(String operatorName){
        return repository.findAllByOperatorName(operatorName);
    }

    public List<Log> listByServiceName(String serviceName){
        return repository.findAllByServiceName(serviceName);
    }

    public List<Log> list(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Log> logs = repository.findAll(sort);
        if(logs.size()>10){
            return logs.subList(0,10);
        }
        return logs;
    }
}
