package com.roulette.com.service;

import com.roulette.com.config.Constants;
import com.roulette.com.domain.Bets;
import com.roulette.com.domain.Game;
import com.roulette.com.domain.Roulette;
import com.roulette.com.domain.enumeration.typeColour;
import com.roulette.com.repository.BetsRepository;
import com.roulette.com.repository.GameRepository;
import com.roulette.com.repository.RouletteRepository;
import com.roulette.com.service.dto.GameDTO;
import com.roulette.com.service.dto.RouletteDTO;
import com.roulette.com.service.mapper.GameMapper;
import com.roulette.com.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    private String colour;

    private final BetsRepository betsRepository;

    private List<Game> gameList = new ArrayList<>();

    private final RouletteRepository rouletteRepository;

    public GameService(GameRepository gameRepository, GameMapper gameMapper, BetsRepository betsRepository, RouletteRepository rouletteRepository) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.betsRepository = betsRepository;
        this.rouletteRepository = rouletteRepository;
    }

    public GameDTO save(GameDTO gameDTO) {
        log.debug("Request to save Game : {}", gameDTO);
        Game game = gameMapper.toEntity(gameDTO);
        game = gameRepository.save(game);
        return gameMapper.toDto(game);
    }

    public Page<GameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Games");
        return gameRepository.findAll(pageable)
                .map(gameMapper::toDto);
    }


    public Optional<GameDTO> findOne(String id) {
        log.debug("Request to get Game : {}", id);
        return gameRepository.findById(id)
                .map(gameMapper::toDto);
    }

    public void delete(String id) {
        log.debug("Request to delete Game : {}", id);
        gameRepository.deleteById(id);
    }

    public List<Game> gameOver(String roulette) {
        List<Game> game = gameRepository.findAllByRoulette(roulette);
        if (game.isEmpty()) {
            return gameOpen(roulette);
        }
        throw new BadRequestAlertException("Access was denied, the roulette wheel is closed!","GAME"," ROULETTE_CLOSED");
    }

    private List<Game> gameOpen(String roulette) {
        long numberGame = (long) (Math.random() * 36 + 1);
        if (numberGame % 2 == 0) {
            colour = "RED";
        }
        colour = "BLACK";
        List<Bets> betsListNumber = betsRepository.findAllByRouletteAndBetNumber(roulette, Math.toIntExact(numberGame));
        betsListNumber.forEach(n -> {
            Game game = new Game();
            game.setColour(n.getColorBet());
            game.setRoulette(roulette);
            game.setEarnedValue(Long.valueOf(n.getBetValue() * 5));
            game.setWinningNumber(numberGame);
            game.setMsn(Constants.MSN_GAME_USER + " dollars: " + n.getBetValue() * 5);
            gameRepository.save(game);
            gameList.add(game);
        });
        List<Bets> betsListColour = betsRepository.findAllByRouletteAndColorBet(roulette, colour);
        betsListColour.forEach(c -> {
            Game game = new Game();
            game.setColour(c.getColorBet());
            game.setRoulette(roulette);
            game.setEarnedValue((new Double(c.getBetValue() * 1.8)).longValue());
            game.setWinningNumber(numberGame);
            game.setMsn(Constants.MSN_GAME_USER + " dollars: " + c.getBetValue() * 1.8);
            gameRepository.save(game);
            gameList.add(game);
        });
        if (gameList.isEmpty()) {
            return inHome(numberGame, roulette, colour);
        }
        return new ArrayList<>(gameList);
    }

    private List<Game> inHome(Long value, String rulette, String colours) {
        Game game = new Game();
        game.setWinningNumber(value);
        game.setRoulette(rulette);
        game.setColour(typeColour.valueOf(colours));
        game.setMsn(Constants.MSN_GAME_SYSTEM);
        gameRepository.save(game);
        gameList.add(game);
        return new ArrayList<>(gameList);
    }
}
