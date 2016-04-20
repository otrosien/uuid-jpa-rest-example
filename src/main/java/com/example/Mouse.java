package com.example;

import static javax.persistence.AccessType.FIELD;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Access(FIELD)
@EntityListeners({AuditingEntityListener.class})
public class Mouse {

    @Id
    @Column(columnDefinition="BINARY(16)")
    @Getter
    // JPA spec prohibits final, but both
    // eclipselink and hibernate actually would be fine with it.
    private UUID id;

    @Version
    @Column(name = "OPT_LOCK", nullable = false)
    @JsonIgnore
    private Long optLock;

    @Basic
    @CreatedDate
    @JsonIgnore
    @Column(name = "CREATED_AT", nullable = false, insertable = true, updatable = false)
    private LocalDateTime createdAt;

    @Basic
    @LastModifiedDate
    @JsonIgnore
    @Column(name = "LAST_MODIFIED_AT", nullable = false, insertable = true, updatable = true)
    private LocalDateTime lastModifiedAt;

    @Getter
    @Setter
    private String name;

    public Mouse() {
        id = DemoApplication.uuidGenerator().generate();
    }

    public Mouse(UUID id) {
        this.id = id;
    }

}
