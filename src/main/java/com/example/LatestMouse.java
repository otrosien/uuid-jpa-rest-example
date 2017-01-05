package com.example;

import static javax.persistence.AccessType.FIELD;

import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Access(FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="LATEST_MOUSE")
@Builder
public class LatestMouse {

    @Id
    @Getter
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "MOUSE_ID", nullable = false, columnDefinition = "BINARY(16) NOT NULL")
    @Getter
    private Mouse mouse;

    public static LatestMouseBuilder builder(Mouse mouse) {
        return new LatestMouseBuilder(mouse);
    }

    public static class LatestMouseBuilder {

        LatestMouseBuilder(Mouse mouse) {
            mouse(mouse);
        }
    }
}
