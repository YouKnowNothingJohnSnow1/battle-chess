package com.example.demo.validator;

import com.example.demo.model.Archer;
import com.example.demo.model.Cannon;
import com.example.demo.model.Transport;
import com.example.demo.model.Unit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CommandValidator {

    private static final long ARCHER_MOVE_DELAY = 5000; // 5 seconds
    private static final long TRANSPORT_MOVE_DELAY = 7000; // 7 seconds
    private static final long ARCHER_SHOOT_DELAY = 10000; // 10 seconds
    private static final long CANNON_SHOOT_DELAY = 13000; //13 seconds


    public boolean canExecuteCommand(Unit unit, String commandType){
        long timeSinceLastAction = Duration.between(unit.getLastActionTime(),
                LocalDateTime.now()).toMillis();
        switch(commandType){
            case "move":
                if( unit instanceof Archer) return timeSinceLastAction >= ARCHER_MOVE_DELAY;
                if( unit instanceof Transport) return timeSinceLastAction >= TRANSPORT_MOVE_DELAY;
                break;
            case "shoot":
                if(unit instanceof Archer) return timeSinceLastAction >= ARCHER_SHOOT_DELAY;
                if( unit instanceof Cannon) return timeSinceLastAction >= CANNON_SHOOT_DELAY;
                break;
        }
        return false;
    }

    public void validateTimeSinceLastAction(Unit unit){
        long timeSinceLastAction = Duration.between(unit.getLastActionTime(),
                LocalDateTime.now()).toMillis();
        if(unit instanceof Archer) {
            if(timeSinceLastAction < 5000) {
                throw new IllegalArgumentException("Za mało czasu upłynęło od ostatniego ruchu łucznika.");
            }
        } else if ( unit instanceof Transport) {
            if(timeSinceLastAction < 7000){
               throw new IllegalArgumentException("Za mało czasu upłynęło od ostatniego ruchu transportu.");
            }
        } else if (unit instanceof Cannon) {
            if(timeSinceLastAction < 13000){
                throw new IllegalArgumentException("Za mało czasu upłynęło od ostatniego strzału armaty.");
            }
        }
        }
}


