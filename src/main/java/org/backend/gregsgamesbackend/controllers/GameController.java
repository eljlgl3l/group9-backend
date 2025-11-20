package org.backend.gregsgamesbackend.controllers;

import org.backend.gregsgamesbackend.dto.request.GameDTO;
import org.backend.gregsgamesbackend.models.Game;
import org.backend.gregsgamesbackend.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/games")
    public List<Game> findAllGames() {
        return gameService.getAllGames();
    }

    // Update an existing game (PUT /games/{id})
    @PutMapping("/games/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable int id, @RequestBody GameDTO dto) {
        Game updatedGame = gameService.saveGame(id, dto);
        return ResponseEntity.ok(updatedGame);
    }

    @PostMapping("/games/create")
    public ResponseEntity<Game> createGame(@RequestBody GameDTO dto) {
        Game created = gameService.createGame(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable int id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/games/search")

    public ResponseEntity<List<Game>> searchGames(
            @RequestParam("query") String query) {

        // 1. Call the service layer to perform the search/filtering
        List<Game> foundGames = gameService.searchGamesByQuery(query);

        // 3. Return the results
        return ResponseEntity.ok(foundGames);
    }
}
