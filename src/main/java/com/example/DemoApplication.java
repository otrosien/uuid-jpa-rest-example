package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@EntityScan(basePackageClasses = {DemoApplication.class, org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.class})
public class DemoApplication {

    private static final TimeBasedGenerator UUID_GENERATOR = Generators.timeBasedGenerator(EthernetAddress.fromInterface());

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    public static UUIDGenerator uuidGenerator() {
        return () -> UUID_GENERATOR.generate();
    }

    @Configuration
    static class MouseRestConfiguration extends RepositoryRestMvcConfiguration {

        @Override
        protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.exposeIdsFor(Mouse.class);
            config.withEntityLookup().//
            forRepository(MouseRepository.class, Mouse::getName, MouseRepository::findByName);
        }
    }
}
