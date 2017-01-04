package com.example;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Access(AccessType.FIELD)
@Table(name="MOUSE", indexes= {
        @Index(columnList="NAME", unique=true)
})
@NoArgsConstructor(access=PRIVATE)
public class Mouse extends AbstractEntity {

    private static final long serialVersionUID = 9132197821372047114L;

    @Getter
    @Setter
    @Column(name = "NAME", nullable = false, insertable = true, updatable = true)
    private String name;

    public Mouse(UUID id) {
        super(id);
    }

    @Override
    public int hashCode() {
        // stick to Object.hashCode() for new entities.
        if (isNew()) {
            return super.hashCode();
        }
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        // stick to Object.equals() for new entities.
        if (isNew())
            return super.equals(obj);
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mouse other = (Mouse) obj;
        if (!getId().equals(other.getId()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("Mouse [id=%s]", getId());
    }

}
