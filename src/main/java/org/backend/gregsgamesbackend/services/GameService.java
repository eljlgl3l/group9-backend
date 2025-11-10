package org.backend.gregsgamesbackend.services;

import org.backend.gregsgamesbackend.models.Game;
import org.backend.gregsgamesbackend.repository.GameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        Pageable limit = PageRequest.of(0,7);
        return gameRepository.findAll(limit).getContent();
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
}
