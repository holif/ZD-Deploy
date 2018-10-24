package com.opopto.deploy.controller;

import com.opopto.deploy.model.ServiceException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.InstanceNotFoundException;
import java.io.EOFException;

@CommonsLog
@ResponseBody
public class ExceptionController {

    @ExceptionHandler(ServiceException.class)
    public Object handle(ServiceException e) {
        log.error("发生异常，错误信息 : "+ e.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public Object handle(Exception e) {
        log.error("发生异常，错误信息 : "+ e.getMessage());
        return   "error";
    }

    @ExceptionHandler(NullPointerException.class)
    public Object handle(NullPointerException e) {
        log.error("发生了空指针异常，检查参数是否正常，错误信息 : "+ e.getMessage());
        return   "请求参数异常";
    }

    @ExceptionHandler(InstanceNotFoundException.class)
    public Object handle(InstanceNotFoundException e) {
        log.error("类的实例化错误，错误信息 : "+ e.getMessage());
        return   "实例化异常";
    }

    @ExceptionHandler(EOFException.class)
    public Object handle(EOFException e) {
        log.error("文件结束符错误，错误信息 : "+ e.getMessage());
        return   "文件结束符错误";
    }

}
