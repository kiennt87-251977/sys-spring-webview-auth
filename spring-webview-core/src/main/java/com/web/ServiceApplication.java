package com.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
@EnableAsync
@Slf4j
@OpenAPIDefinition(info = @Info(title = "Core Service API", version = "1.0"),
//        servers = {@Server(url = "/", description = "Default Server URL")},
        security = {@SecurityRequirement(name = "Authorization")})
public class ServiceApplication {
    public static ApplicationContext applicationContext;

    public static void main(String[] args) {
        log.info(">>>>> Start ServiceApplication");
//        ConfigurationSystem.setConfig();
        log.info(">>>>> Set Configuration done");
        applicationContext = SpringApplication.run(ServiceApplication.class, args);
        log.info(">>>>> END ServiceApplication");
    }

}

