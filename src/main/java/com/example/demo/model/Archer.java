package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.service.GameService;

public class Archer extends Unit {

    @Autowired
    private GameService gameService;
    public void shoot(int targetX, int targetY) {
        // Logika strzału łucznika
        // Łucznik strzela w linii prostej w jednym z kierunków
        if ((targetX == this.getX() && targetY != this.getY()) ||
                (targetY == this.getY() && targetX != this.getX())) {
            Unit targetUnit = findUnitAtPosition(targetX, targetY);
            if (targetUnit != null) {
                targetUnit.isDestroyed(); // niszczenie jesdnostki jeśli strzał trafia
            }
        } else {
            throw new IllegalArgumentException("Łucznik może strzelać tylko w linii prostej.");
        }
    }

    private Unit findUnitAtPosition(int targetX, int targetY) {
        return gameService.findUnitAtPosition(targetX, targetY);
    }

    public void move(int targetX, int targetY) {
        // Logika ruchu łucznika (jedno pole)
        if (Math.abs(targetX - this.getX()) + Math.abs(targetY - this.getY()) == 1) {
            this.setX(targetX);
            this.setY(targetY);
            incrementMoveCount();
        } else {
            throw new IllegalArgumentException("Łucznik może się poruszyć tylko o jedno pole.");
        }
    }
}
