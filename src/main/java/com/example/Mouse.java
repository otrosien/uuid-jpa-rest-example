package com.example;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.uuid.NoArgGenerator;

@Entity
public class Mouse {

    @Transient
    private static final NoArgGenerator UUID_GENERATOR = DemoApplication.uuidGenerator();

    @Id
    @Column(columnDefinition="BINARY(16)")
    // JPA spec prohibits final, but 
    // eclipselink and hibernate actually would be fine.
    private UUID id;

    private String name;

    public Mouse() {
        id = UUID_GENERATOR.generate();
    }

    public Mouse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
