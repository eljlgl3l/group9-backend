package org.backend.gregsgamesbackend.controllers;

import org.backend.gregsgamesbackend.models.Game;
import org.backend.gregsgamesbackend.services.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // to allow frontend, whatever port its requesting from
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/test")
    public List<Game> getTesting() {
        return gameService.getAllGames();
    }

//    @GetMapping("/games/developer/{developerName}")
//    public List<Game> findGamesByDeveloper(
//            @PathVariable String developerName,
//            @RequestParam(name = "limit", defaultValue = "5") int limit) {
//        return gameService.getGamesByPublisher(developerName, limit);
//    }

    @GetMapping("/games/console/{consoleName}")
    public List<Game> findGamesByConsole(
            @PathVariable String consoleName,
            @RequestParam(name = "limit", defaultValue = "5") int limit)
    {
        return gameService.getGamesByConsole(consoleName, limit);
    }

}
