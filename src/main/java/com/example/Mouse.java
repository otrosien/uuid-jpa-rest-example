package com.example;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToOne;
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

    @Getter
    @Setter
    @Column(name = "NAME", nullable = false, insertable = true, updatable = true)
    private String name;

    @Getter
    @Setter
    @Column(name = "AGE", nullable = true, insertable = true, updatable = true)
    private Integer age;

    @Getter
    @Setter
    @OneToOne(cascade = ALL, mappedBy = "mouse", orphanRemoval=true)
    LatestMouse latest = LatestMouse.builder(this).build();

    public Mouse(UUID uuid) {
        super(uuid);
    }

    @Override
    public String toString() {
        return String.format("Mouse [id=%s]", id);
    }

    @Override
    public boolean isNew() {
        return true;
    }

}
