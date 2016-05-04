package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Configuration
    static class MouseRestConfiguration extends RepositoryRestMvcConfiguration {

        @Override
        protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config
                .exposeIdsFor(Mouse.class)
                .withEntityLookup()
                    .forRepository(MouseRepository.class, Mouse::getName, MouseRepository::findByName);
        }
    }
}
