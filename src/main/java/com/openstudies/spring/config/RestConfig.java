package com.openstudies.spring.config;

import com.openstudies.webservices.rest.UserRestWebService;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


public class RestConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(UserRestWebService.class);
        return classes;
    }
}
