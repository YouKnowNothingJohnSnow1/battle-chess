package com.example.demo.model;

public class Transport extends Unit{

    public void move(int newX, int newY) {
        // Logika ruchu transportu (1, 2 lub 3 pola)
        int distance = Math.abs(newX - this.getX()) + Math.abs(newY - this.getY());
        if (distance >= 1 && distance <= 3) {
            this.setX(newX);
            this.setY(newY);
            incrementMoveCount();
        } else {
            throw new IllegalArgumentException("Transport może się poruszyć o 1, 2 lub 3 pola.");
        }
    }
}
