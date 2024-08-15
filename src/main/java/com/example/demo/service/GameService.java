package com.example.demo.service;

import jakarta.transaction.Transactional;
import com.example.demo.model.Archer;
import com.example.demo.model.Cannon;
import com.example.demo.model.Game;
import com.example.demo.model.Unit;
import com.example.demo.repository.CommandRepository;
import com.example.demo.repository.GameRepository;
import com.example.demo.repository.UnitRepository;
import com.example.demo.validator.CommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private CommandRepository commandRepository;
    private GameRepository gameRepository;
    private UnitRepository unitRepository;
    private CommandValidator commandValidator;

    @Autowired
    public GameService(CommandRepository commandRepository, GameRepository gameRepository,
                       UnitRepository unitRepository, CommandValidator commandValidator) {
        this.commandRepository = commandRepository;
        this.gameRepository = gameRepository;
        this.unitRepository = unitRepository;
        this.commandValidator = commandValidator;
    }

    public Game createNewGame(int boardWidth, int boardHeight){
        // usuwanie poprzedniej gry
        gameRepository.deleteAll();
        commandRepository.deleteAll();

        // tworzenie nowej gry
        Game game = new Game();
        game.setBoardWidth(boardWidth);
        game.setBoardHeight(boardHeight);
        game = gameRepository.save(game);

        // losowe rozmieszenie jednostek na szachownicy
        initializeUnits(game);
        return game;
    }

    public void initializeUnits(Game game){
        Random random = new Random();
        List<Unit> units = new ArrayList<>();
        //tworzenie jednostek wg konfiguracji
        for(Unit unit : units){
            unit.setGame(game);
            unit.setX(random.nextInt(game.getBoardWidth()));
            unit.setY(random.nextInt(game.getBoardHeight()));
            unitRepository.save(unit);
       }
    }

    // metoda zwraca listę jednostek dla danego koloru
    public List<Unit> getUnits(Long gameId, String color){
        return unitRepository.findByGameIdAndColor(gameId, color);
    }

    //metoda wykonuje komendę dla danej jednostki
    @Transactional
    public Unit executeCommand(Long unitId, String commandType, int targetX, int targetY){
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        commandValidator.validateTimeSinceLastAction(unit);
        //logika wykonoania komendy w zalezności od jej typu
        switch(commandType){
            case "move":
                moveUnit(unit, targetX, targetY);
                break;
            case "shoot":
                shoot(unit, targetX, targetY);
                break;
        }
        unit.setLastActionTime(LocalDateTime.now());
        return unitRepository.save(unit);
    }

    private void moveUnit(Unit unit, int targetX, int targetY){
        // sprawdzenie czy ruch jest możliwy z zasadami gry
        unit.setX(targetX);
        unit.setY(targetY);
        unit.incrementMoveCount();
    }

    private void shoot(Unit unit, int targetX, int targetY){
        if (unit instanceof Archer) {
            // Logika strzelania dla łucznika
            Archer archer = (Archer) unit;
            archer.shoot(targetX, targetY);
        } else if (unit instanceof Cannon) {
            // Logika strzelania dla armaty
            Cannon cannon = (Cannon) unit;
            cannon.shoot(targetX, targetY);
        } else {
            throw new IllegalArgumentException("Jednostka nie ma możliwości strzału.");
        }
    }

    // komenda dla jednostki z rozkazem przypadkowego ruchu
    public Unit executeRandomCommand(Long unitId){
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(()-> new RuntimeException("Unit not found"));

        commandValidator.validateTimeSinceLastAction(unit);

        // wykonanie losowej komendy
        Random random = new Random();
        int randomDirection = random.nextInt(4);
        int targetX = unit.getX(), targetY = unit.getY();

        switch (randomDirection){
            case 0: targetX += 1; break; // prawo
            case 1: targetX -= 1; break; // lewo
            case 2: targetY += 1; break; // góra
            case 3: targetY -= 1; break; // dół
        }

        moveUnit(unit, targetX, targetY);

        unit.setLastActionTime(LocalDateTime.now());
        return unitRepository.save(unit);
    }

    public Unit findUnitAtPosition(int x, int y) {
        return unitRepository.findByXAndY( x, y);
    }
}

