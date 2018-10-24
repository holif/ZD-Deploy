package com.opopto.deploy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/app").setViewName("app");
        registry.addViewController("/node").setViewName("node");
        registry.addViewController("/map").setViewName("map");
        registry.addViewController("/deploy").setViewName("deploy");
        registry.addViewController("/log").setViewName("log");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("logout");
    }

}
