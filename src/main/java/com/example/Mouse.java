package com.example;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Access(AccessType.FIELD)
@EntityListeners({AuditingEntityListener.class})
@Table(name="MOUSE", indexes= {
        @Index(columnList="NAME", unique=true)
})
@NoArgsConstructor(access=PRIVATE)
public class Mouse implements Persistable<UUID> {

    private static final long serialVersionUID = 9132197821372047114L;

    @Id
    @Column(columnDefinition="BINARY(16)")
    @GeneratedValue(generator="uuid-sequence")
    @Getter
    // JPA specification prohibits final, but both
    // EclipseLink and Hibernate would actually be fine with it.
    private UUID id;

    @Version
    @Column(name = "OPT_LOCK", nullable = false)
    // excluded by default, so this annotation is 
    // purely for documentation purposes.
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
    @Column(name = "NAME", nullable = false, insertable = true, updatable = true)
    private String name;

    public Mouse(UUID id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }

    @Override
    public int hashCode() {
        // stick to object.hashCode for new entities.
        if (isNew()) {
            return super.hashCode();
        }
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        // stick to object.equals for new entities.
        if (isNew())
            return super.equals(obj);
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mouse other = (Mouse) obj;
        if (!id.equals(other.id))
            return false;
        return true;
    }

}
