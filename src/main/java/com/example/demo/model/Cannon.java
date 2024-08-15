package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.service.GameService;

public class Cannon extends Unit {

    @Autowired
    private GameService gameService;
    public void shoot(int targetX, int targetY){
        // logika strzelania po skosie
        int deltaX = Math.abs(targetX - this.getX());
        int deltaY = Math.abs(targetY - this.getY());

        // armata może strzelać w linii prostej lub po skosie
        if(deltaX == deltaY || deltaX == 0 || deltaY == 0){
            //strzał jest legalny, więc sprawdzamy czy na docelowej pozycji jest jednostka
            Unit targetUnit = findUnitAtPosition(targetX, targetY);
            if(targetUnit != null) {
                targetUnit.isDestroyed(); // niszczenie jednostki, jeśli strzał trafia
            }
        } else {
            throw new IllegalArgumentException("Armata może strzelać tylko w linii prostej lub po skosie.");
        }
    }

    private Unit findUnitAtPosition(int targetX, int targetY) {
        return gameService.findUnitAtPosition(targetX, targetY);
    }
}
