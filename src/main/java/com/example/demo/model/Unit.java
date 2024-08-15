package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "units")
public class Unit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long id;
    @Column(name = "unit_type")
    private String type;
    @Column(name = "unit_x")
    private int x;
    @Column(name = "unit_y")
    private int y;
    @Column(name = "unit_color")
    private String color;
    @Column(name = "unit_destroyed")
    private boolean destroyed = false;
    @Column(name = "unit_moveCount")
    private int moveCount = 0;
    @Column(name = "unit_lastActionTime")
    private LocalDateTime lastActionTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Game game;

    public void incrementMoveCount(){
        this.moveCount++;
    }
    public void destroy() {
        this.destroyed = true;
    }
}
