package com.example;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class AbstractEntity implements Persistable<UUID> {

    @Id
    @Column(columnDefinition="BINARY(16)")
    @GeneratedValue(generator="uuid-sequence")
    @Getter
    // map technical data like the ID to a different "namespace" using underscore as prefix.
    // and make sure no one is setting this directly via REST
    @JsonProperty(value="_id", access=JsonProperty.Access.READ_ONLY)
    // JPA specification prohibits final, but both
    // EclipseLink and Hibernate would actually be fine with it.
    private UUID id;

    @Basic
    @Version
    @Column(name = "OPT_LOCK", nullable = false)
    // excluded by default, so this annotation is 
    // purely for documentation purposes.
    @JsonIgnore
    private Long optLock;

    @Basic
    @CreatedDate
    @JsonIgnore
    @Column(name = "CREATED_AT", nullable = false, insertable = true, updatable = false, columnDefinition = "TIMESTAMP NOT NULL")
    private Instant createdAt;

    @Basic
    @LastModifiedDate
    @JsonIgnore
    @Column(name = "LAST_MODIFIED_AT", nullable = false, insertable = true, updatable = true, columnDefinition = "TIMESTAMP NOT NULL")
    private Instant lastModifiedAt;

    protected AbstractEntity() {}

    public AbstractEntity(UUID id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }

}
