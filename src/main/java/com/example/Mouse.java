package com.example;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Access(AccessType.FIELD)
@Table(name="MOUSE", indexes= {
        @Index(columnList="NAME", unique=false)
})
@NoArgsConstructor(access=PRIVATE)
@EqualsAndHashCode(of="name", callSuper=false)
public class Mouse extends ImmutableEntity {

    private static final long serialVersionUID = 9132197821372047114L;

    // stable identifier.
    @Getter
    @Column(name = "NAME", nullable = false, insertable = true, updatable = false)
    private String name;

    @Getter
    @Setter
    @Column(name = "AGE", nullable = true, insertable = true, updatable = true)
    private Integer age;

    @Override
    public String toString() {
        return String.format("Mouse [id=%s]", id);
    }

    @Override
    public boolean isNew() {
        return true;
    }

}
