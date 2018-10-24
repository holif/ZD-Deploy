package com.opopto.deploy.service;

import com.opopto.deploy.jpa.AppRepository;
import com.opopto.deploy.model.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {

    @Autowired
    private AppRepository appRepository;

    public App save(App app){
        return appRepository.save(app);
    }

    public void update(App app){
        appRepository.updateById(app.getProject(), app.getFileName(), app.getServiceName(), app.getDeployPath(), app.getId());
    }

    public void delete(Integer id){
        appRepository.delete(id);
    }

    public App one(Integer id){
        return appRepository.findOne(id);
    }

    public App oneByFileName(String fileName){
        return appRepository.findAppByFileName(fileName);
    }

    public List<App> list(){
        return appRepository.findAll();
    }

}
