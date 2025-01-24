package com.web.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaServer
@SpringBootApplication
@EnableScheduling
public class RegistryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistryServiceApplication.class, args);
    }

}
