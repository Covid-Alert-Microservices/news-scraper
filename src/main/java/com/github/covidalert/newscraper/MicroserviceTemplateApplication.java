package com.github.covidalert.newscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MicroserviceTemplateApplication extends SpringBootServletInitializer
{

    public static void main(String[] args)
    {
        SpringApplication.run(MicroserviceTemplateApplication.class, args);
    }

}
