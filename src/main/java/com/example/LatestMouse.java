package com.example;

import static javax.persistence.AccessType.FIELD;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Access(FIELD)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Table(name = "LATEST_MOUSE", indexes= {
        @Index(columnList="ID", unique=true)
})
public class LatestMouse {

    @Id
    @Getter
    @Column(name = "NAME", nullable = false, insertable = true, updatable = false)
    private String name;

    @OneToOne(optional=false)
    @Getter
    private Mouse mouse;

}
