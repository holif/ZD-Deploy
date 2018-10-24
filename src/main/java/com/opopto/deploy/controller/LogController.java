package com.opopto.deploy.controller;

import com.opopto.deploy.model.Log;
import com.opopto.deploy.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@ResponseBody
@RequestMapping("/log")
public class LogController extends ExceptionController {

    @Autowired
    private LogService logService;

    @RequestMapping("/list")
    public Collection<Log> list() {
        return logService.list();
    }

    @RequestMapping("/list/operator/{name}")
    public Collection<Log> listByOper(@PathVariable(value = "name") String name) {
        return logService.listByOperatorName(name);
    }

    @RequestMapping("/list/service/{name}")
    public Collection<Log> listByServiceName(@PathVariable(value = "name") String name) {
        return logService.listByServiceName(name);
    }

}
