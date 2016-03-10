package com.example;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Mouse {

    @Id
    @Column(columnDefinition="BINARY(16)")
    @Getter
    private final UUID id;

    @Getter
    @Setter
    private String name;

    public Mouse() {
        id = UUID.randomUUID();
    }

    public Mouse(UUID id) {
        this.id = id;
    }
}
