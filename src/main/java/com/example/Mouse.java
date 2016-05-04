package com.example;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.domain.Persistable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access=PRIVATE)
public class Mouse implements Persistable<UUID> {

    private static final long serialVersionUID = 9132197821372047114L;

    @Id
    @Column(columnDefinition="BINARY(16)")
    @GeneratedValue(generator="system-uuid")
    @Getter
    private UUID id;

    @Getter
    @Setter
    private String name;

    public Mouse(UUID id) {
        this.id = id;
    }

    @Override
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
