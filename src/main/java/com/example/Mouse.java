package com.example;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.uuid.NoArgGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Mouse {

    @Transient
    private static final NoArgGenerator UUID_GENERATOR = DemoApplication.uuidGenerator();

    @Id
    @Column(columnDefinition="BINARY(16)")
    @Getter
    private final UUID id;

    @Getter
    @Setter
    private String name;

    public Mouse() {
        id = UUID_GENERATOR.generate();
    }

    public Mouse(UUID id) {
        this.id = id;
    }
}
