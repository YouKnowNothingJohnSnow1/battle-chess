package com.example.demo.controller;

import com.example.demo.model.Game;
import com.example.demo.model.Unit;
import com.example.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/new")
    public Game createNewGame(@RequestParam int boardWidth, @RequestParam int boardHeight){
        return gameService.createNewGame(boardWidth, boardHeight);
    }

    @GetMapping("/{gameId}/units")
    public List<Unit> getUnits(@PathVariable Long gameId, @RequestParam String color){
        return gameService.getUnits(gameId, color);
    }

    @PostMapping("/{gameId}/unit/{unitId}/command")
    public Unit executeCommand(
            @PathVariable Long gameId,
            @PathVariable Long unitId,
            @RequestParam String commandType,
            @RequestParam int targetX,
            @RequestParam int targetY){
        return gameService.executeCommand(unitId, commandType,targetX, targetY);
    }
}

