package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@SpringBootApplication
public class MouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MouseApplication.class, args);
    }

    @Configuration
    static class MouseRestConfiguration extends RepositoryRestMvcConfiguration {

        @Override
        protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config
                .setReturnBodyOnCreate(true)
                .setReturnBodyOnUpdate(true)
                .exposeIdsFor(Mouse.class)
                .withEntityLookup()
                    .forRepository(MouseRepository.class, Mouse::getName, MouseRepository::findLatestMouseByName);
        }
    }
}
