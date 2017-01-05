package com.example;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class ImmutableEntity implements Persistable<UUID> {

    @Id
    @Column(columnDefinition="BINARY(16)")
    @Getter
    @JsonProperty(value="_id", access=JsonProperty.Access.READ_ONLY)
    protected UUID id;

    @Basic
    @CreatedDate
    @JsonIgnore
    @Column(name = "CREATED_AT", nullable = false, insertable = true, updatable = false, columnDefinition = "TIMESTAMP NOT NULL")
    private Instant createdAt;

    protected ImmutableEntity() {}

    public ImmutableEntity(UUID id) {
        this.id = id;
    }

}
