package org.example.spring26.config;

import org.example.spring26.HelloServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<HelloServlet> servletRegistrationBean(){

        return new ServletRegistrationBean<>(new HelloServlet(),"/hello");
    }

}
