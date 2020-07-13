package com.openstudies.spring.config.mvc;

import com.openstudies.spring.config.HibernateConfiguration;
import com.openstudies.spring.config.security.WebSecurityConfig;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {



    @Override
    public void onStartup(ServletContext servletContext) {

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebAppConfig.class, WebSecurityConfig.class ,
                HibernateConfiguration.class);
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(context);
        servletContext.addListener(contextLoaderListener);
        context.setServletContext(servletContext);
        ServletRegistration.Dynamic mvcDispatcher = servletContext.addServlet("mvcDispatcher",
                new DispatcherServlet(context));
        mvcDispatcher.setLoadOnStartup(1);
        mvcDispatcher.addMapping("/");
        ServletRegistration.Dynamic cxfDispatcher = servletContext.addServlet("cxfDispatcher",
                new CXFServlet());
        cxfDispatcher.setLoadOnStartup(2);
        cxfDispatcher.addMapping("/soap/*");
    }

}
