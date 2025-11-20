package org.backend.gregsgamesbackend.services;

import jakarta.transaction.Transactional;
import org.backend.gregsgamesbackend.dto.request.GameDTO;
import org.backend.gregsgamesbackend.dto.request.OrderItemDTO;
import org.backend.gregsgamesbackend.models.Game;
import org.backend.gregsgamesbackend.models.Platform;
import org.backend.gregsgamesbackend.repository.GameRepository;
import org.backend.gregsgamesbackend.repository.PlatformRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final PlatformRepository platformRepository;

    public GameService(GameRepository gameRepository, PlatformRepository platformRepository) {
        this.gameRepository = gameRepository;
        this.platformRepository = platformRepository;
    }

    public List<Game> getAllGames() {
//        Pageable limit = PageRequest.of(0,7);
//        return gameRepository.findAll(limit).getContent();
        return gameRepository.findAll();
    }

//    public List<Game> getGamesByPublisher(String publisherName, int limit) {
//        // Just call the method you defined!
//        Pageable pageRequest = PageRequest.of(0, limit);
//        Page<Game> gamePage = gameRepository.findByPublisher(publisherName, pageRequest);
//
//        return gamePage.getContent();
//    }

    public List<Game> getGamesByConsole(String consoleName, int limit) {
//        if (limit > 0) { OLD CODE (WILL HAVE TO LOOK AT AGAIN LATER)
//            Pageable pageRequest = PageRequest.of(0, limit);
//            return gameRepository.findByDeveloperIgnoreCase(consoleName, pageRequest).getContent();
//        }
        return gameRepository.findByPlatform_TitleIgnoreCase(consoleName);
    }

    public Game saveGame(int gameId, GameDTO dto) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (!game.isPresent()) {
            throw new RuntimeException("Game not found");
        }
        Game gameToUpdate = game.get();
        gameToUpdate.setTitle(dto.getTitle());
        gameToUpdate.setDeveloper(dto.getDeveloper());
        gameToUpdate.setPrice(dto.getPrice());
        gameToUpdate.setQuantity_in_stock(dto.getQuantity());
        gameToUpdate.setImage_url(dto.getImage_url());
        Optional<Platform> platform = platformRepository.findById(dto.getPlatformId());
        if (platform.isPresent()) {
            gameToUpdate.setPlatform(platform.get());
        } else throw new RuntimeException("Platform not found");

        return gameRepository.save(gameToUpdate);
    }
    // TODO: use mapstruct later on for dto mapping
    public Game createGame(GameDTO dto) {
        Platform platform = platformRepository.findById(dto.getPlatformId())
                .orElseThrow(() -> new RuntimeException("Platform not found"));

        Game newGame = new Game();
        newGame.setTitle(dto.getTitle());
        newGame.setDeveloper(dto.getDeveloper());
        newGame.setPublisher(dto.getPublisher());
        newGame.setPlatform(platform);
        newGame.setReleaseDate(dto.getReleaseDate());
        newGame.setPrice(dto.getPrice());
        newGame.setQuantity_in_stock(dto.getQuantity());
        newGame.setImage_url(dto.getImage_url());
        return gameRepository.save(newGame);
    }

    public void deleteGame(int id) {
        if (!gameRepository.existsById(id)) {
            throw new RuntimeException("Game not found");
        }
        gameRepository.deleteById(id);
    }

    public void updateQuantityFromOrder(List<OrderItemDTO> itemDtos) {
        for (OrderItemDTO dto : itemDtos) {
            Optional<Game> game = gameRepository.findById(Math.toIntExact(dto.getGameId()));
            if (game.isEmpty()) {
                throw new RuntimeException("Game cannot be found when updating quantity! (updateQuantityFromOrder)");
            }
            Game gameFound = game.get();
            int stock = gameFound.getQuantity_in_stock() - dto.getQuantity();
            gameFound.setQuantity_in_stock(stock);
            gameRepository.save(gameFound);
        }
    }

    public List<Game> searchGamesByQuery(String query) {
        // Delegate the actual search query to the repository
        return gameRepository.findByTitleContainingIgnoreCase(query);
    }
}
