package org.springframework.samples.petclinic.game;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.gameProperties.GameProperties;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Minigame;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.util.RestPreconditions;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/games")
@Tag(name = "Games", description = "The Game management API")
@SecurityRequirement(name = "bearerAuth")
public class GameRestController {

    private final GameService gameService;

    @Autowired
    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> findAllGames() {
        return gameService.findAll();
    }

    @GetMapping("/{id}")
    public Game findById(@PathVariable("id") Integer id) {
        return gameService.findById(id);
    }

    @GetMapping("/{id}/rounds")
    public List<Round> findRounds(@PathVariable("id") Integer id) {
        return gameService.findRoundsById(id);
    }
 //Lista por user id
    @GetMapping("/list/{id}")
    public List<Game> findByUserId(@PathVariable("id") Integer id) {
        return gameService.findByUserId(id);
    }


    @GetMapping("/{id}/competitive")
    public Boolean findIsCompetitiveById(@PathVariable("id") Integer id) {
        Game g = gameService.findById(id);
        return g.getIsCompetitive();
    }

    @GetMapping("/{id}/players")
    public List<Player> findPlayersByGame(@PathVariable("id") Integer gameId) {
        Game g= gameService.findById(gameId);
        return gameService.findPlayersByGame(g);
    }

    @GetMapping("/state_by/{id}")
    public GameState findStateByGameId(@PathVariable("id") Integer id) {
        return gameService.findStateByGameId(id);
    }

    private String generateRandomCode() {
        return RandomStringUtils.randomAlphabetic(5).toUpperCase();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Game> create(@RequestBody @Valid Game game) {
        String randomCode = generateRandomCode();
        Game newGame = new Game();
        BeanUtils.copyProperties(game, newGame, "id");
        List<Round> rounds = new ArrayList<>();
        newGame.setRounds(rounds);
        newGame.setGameCode(randomCode);
        List<GameProperties> gp = new ArrayList<>();
        newGame.setGameProperties(gp);
        if (game.getLimitedByRound() != null) {
            if (newGame.getLimitedByRound() == true) {
                newGame.setRoundLimit(game.getRoundLimit());
                for(int i=0; i<game.getRoundLimit(); i++){
                    Round r= new Round();
                    r.setIsOnGoing(false);
                    rounds.add(r);
                    if(i%2==0){
                        r.setMinigame(Minigame.TORREINFERNAL);
                    }else{
                        r.setMinigame(Minigame.PATATACALIENTE);
                    }
                } 
            }else {
                newGame.setTimeLimit(game.getTimeLimit());
                Integer roundNumber;
                if(newGame.getTimeLimit() == 5) {
                    roundNumber = 3;
                } else{
                    roundNumber = 5;
                }
                for(int i=0; i<roundNumber; i++){
                    Round r= new Round();
                    r.setIsOnGoing(false);
                    rounds.add(r);
                    if(i%2==0){
                        r.setMinigame(Minigame.TORREINFERNAL);
                    }else{
                        r.setMinigame(Minigame.PATATACALIENTE);
                    }
                } 
            }
            newGame.setRounds(rounds);
        }
        newGame.setIsCompetitive(game.getIsCompetitive());
        Game savedGame;
        savedGame = this.gameService.save(newGame);
        return new ResponseEntity<>(savedGame, HttpStatus.CREATED);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<Game> update(@PathVariable("gameId") Integer gameId, @RequestBody @Valid GameDTO game) {
        RestPreconditions.checkNotNull(gameService.findById(gameId), "Game", "ID", gameId);
        Game updatedGame = gameService.findById(gameId);
        if(updatedGame == null) {
            throw new ResourceNotFoundException("No encuentro la partida con id: " + gameId);
        }

        updatedGame.setIsCompetitive(game.getIsCompetitive());
        updatedGame.setState(GameState.valueOf(game.getState().toString()));
        updatedGame.setTimeLimit(game.getTimeLimit());
        updatedGame.setLimitedByRound(game.getLimitedByRound());
        updatedGame.setRoundLimit(game.getRoundLimit());
        updatedGame.setRounds(game.getRounds());

		return new ResponseEntity<>(this.gameService.updateGame(updatedGame, gameId), HttpStatus.OK);
    }

     @PutMapping("/{gameId}/rounds/update")
    public ResponseEntity<Game> updateRounds(@PathVariable("gameId") Integer gameId, Round round) {
        RestPreconditions.checkNotNull(gameService.findById(gameId), "Game", "ID", gameId);
        Game updatedGame = gameService.findById(gameId);
        if(updatedGame == null) {
            throw new ResourceNotFoundException("No encuentro la partida con id: " + gameId);
        }
        List<Round> lr= updatedGame.getRounds();
        lr.add(round);
        updatedGame.setRounds(lr);

		return new ResponseEntity<>(this.gameService.updateGame(updatedGame, gameId), HttpStatus.OK);
    }

    @PutMapping("/startGame/{gameId}")
    public void startGame(@PathVariable("gameId") Integer gameId) {
        Game game = gameService.findById(gameId);
        if (game == null) {
            throw new ResourceNotFoundException("No existe el juego con id: " + gameId);
        }
        game.setId(game.getId());
        game.setIsCompetitive(game.getIsCompetitive());
        game.setGameCode(game.getGameCode());
        game.setLimitedByRound(game.getLimitedByRound());
        game.setRoundLimit(game.getRoundLimit());
        game.setTimeLimit(game.getTimeLimit());
        game.setFinishDateTime(null);
        game.setGameProperties(game.getGameProperties());

        game.setState(GameState.ONGOING);
        game.setStartDate(LocalDateTime.now());
        gameService.save(game);
    }

    @PutMapping("/finishGame/{gameId}")
    public void finishGame(@PathVariable("gameId") Integer gameId) {
        Game game = gameService.findById(gameId);
        if (game == null) {
            throw new ResourceNotFoundException("No existe el juego con id: " + gameId);
        }
        game.setId(game.getId());
        game.setIsCompetitive(game.getIsCompetitive());
        game.setGameCode(game.getGameCode());
        game.setLimitedByRound(game.getLimitedByRound());
        game.setRoundLimit(game.getRoundLimit());
        game.setTimeLimit(game.getTimeLimit());
        game.setFinishDateTime(LocalDateTime.now());
        game.setGameProperties(game.getGameProperties());

        game.setState(GameState.FINISHED);
        game.setStartDate(game.getStartDate());
        gameService.save(game);
    }

    @GetMapping("/state/{state}")
    public List<Game> findAllByState(@PathVariable("state") GameState state) {
        return gameService.findAllByState(state);
    }

    @GetMapping("/gameCode/{gameCode}")
    public Game findGameByCodeGame(@PathVariable("gameCode") String code) {
        return gameService.findGameByGameCode(code);
    }

    @PutMapping("/isWinner/{gameId}")
    public void updateIsWinner(@PathVariable("gameId") Integer gameId) {
        gameService.setGameWinner(gameId);
    }
    
////////////////////////////


    

@GetMapping("/numOfGames/{username}")
public Integer countGamesByUserName(@PathVariable("username") Integer username) {
    return gameService.countGamesByUserName(username);
}

@GetMapping("/timePlayed/{username}")
public Double timePlayedByUserName(@PathVariable("username") Integer username) {
    return gameService.timePlayedByUserName(username);
}

@GetMapping("/avgTimePlayed/{username}")
public Double averageTimePlayedByUserName(@PathVariable("username") Integer username) {
    return gameService.averageTimePlayedByUserName(username);
}

@GetMapping("/maxTimePlayed/{username}")
public Double maxTimePlayedByUserName(@PathVariable("username") Integer username) {
    return gameService.maxTimePlayedByUserName(username);
}

@GetMapping("/minTimePlayed/{username}")
public Double minTimePlayedByUserName(@PathVariable("username") Integer username) {
    return gameService.minTimePlayedByUserName(username);
}

@GetMapping("/numOfCompetitiveGames/{username}")
public Integer countCompetitiveGamesByUserName(@PathVariable("username") Integer username) {
    return gameService.countCompetitiveGamesByUserName(username);
}

@GetMapping("/numOfNotCompetitiveGames/{username}")
public Integer countNotCompetitiveGamesByUserName(@PathVariable("username") Integer username) {
    return gameService.countNotCompetitiveGamesByUserName(username);
}

@GetMapping("/numOfCompetitiveGamesWon/{username}")
public Integer countCompetitiveGamesWonByUserName(@PathVariable("username") Integer username) {
    return gameService.countVictoriesRankedByUserName(username);
}

@GetMapping("/numOfNotCompetitiveGamesWon/{username}")
public Integer countNotCompetitiveGamesWonByUserName(@PathVariable("username") Integer username) {
    return gameService.countVictoriesFriendlyByUserName(username);
}


@GetMapping("player/victoriesRanked")
public Map<String,Integer> getPlayers() {
    return gameService.usuariosXvictoriasRanked();
}
//Logros

@GetMapping("/juegaTuPrimeraPartida/{id}")
public Boolean juegaTuPrimeraPartida(@PathVariable("id") Integer id) {
    return gameService.juegaTuPrimeraPartida(id);
}

@GetMapping("/juega5partidas/{id}")
public Boolean juega5partidas(@PathVariable("id") Integer id) {
    return gameService.juega5partidas(id);
}

@GetMapping("/ganaPrimeraPartida/{id}")
public Boolean ganaPrimeraPartida(@PathVariable("id") Integer id) {
    return gameService.ganaPrimeraPartida(id);
}

@GetMapping("/gana5partidas/{id}")
public Boolean gana5partidas(@PathVariable("id") Integer id) {
    return gameService.gana5partidas(id);
}

@GetMapping("/juegaMasde100min/{id}")
public Boolean juegaMasde100min(@PathVariable("id") Integer id) {
    return gameService.juegaMasde100min(id);
}

}