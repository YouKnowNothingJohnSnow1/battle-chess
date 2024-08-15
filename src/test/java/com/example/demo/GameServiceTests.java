package com.example.demo;

import com.example.demo.model.Game;
import com.example.demo.model.Unit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.service.GameService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceTests {

    @Autowired
    private GameService gameService;

    @Test
    public void testCreateNewGame() {
        Game game = gameService.createNewGame(8,8);
        assertNotNull(game.getId());
        assertEquals(8, game.getBoardWidth());
        assertEquals(8, game.getBoardHeight());
    }

    @Test
    public void testExecuteCommand() {
        Game game = gameService.createNewGame(8, 8);
        Unit unit = game.getUnits().get(0);
        assertNotNull(unit.getId());
        Unit updatedUnit = gameService.executeCommand(unit.getId(), "move", 3, 3);
        assertEquals(3, updatedUnit.getX());
        assertEquals(3, updatedUnit.getY());
    }

    @Test
    public void testRandomMoveCommand(){
        Game game = gameService.createNewGame(8,8);
        Unit unit = gameService.getUnits(game.getId(), "white").get(0);
        Unit updatedUnit = gameService.executeRandomCommand(unit.getId());
        assertNotNull(updatedUnit);
        assertNotEquals(unit.getX(), updatedUnit.getX());
        assertEquals(unit.getY(), updatedUnit.getY());
    }

}
