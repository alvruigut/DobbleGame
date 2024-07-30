package org.springframework.samples.petclinic.gameProperties;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/gameProperties")
public class GamePropertiesController {
    
    private final GamePropertiesService gamePropertiesService;
    private final UserService userService;

    @Autowired
    public GamePropertiesController(GamePropertiesService gamePropertiesService, UserService userService){
        this.gamePropertiesService=gamePropertiesService;
        this.userService=userService;

    }

    @GetMapping(value= "/all")
    public ResponseEntity<List<GameProperties>> getAll(){
        return new ResponseEntity<>((List<GameProperties>) gamePropertiesService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/games/{gameId}/id")
    public List<GameProperties> findGamePropertiesByGameId(@PathVariable("gameId") Integer g){
        return gamePropertiesService.findGamePropertiesByGameId(g);
    }

    @GetMapping("/games/{game}")
    public GameProperties findGamePropertiesByGame(@PathVariable("game") Game g){
        return gamePropertiesService.findGamePropertiesByGame(g);
    }

    @GetMapping("/player/{player}")
    public GameProperties findGamePropertiesByPlayer(@PathVariable("player") Player p){
        return gamePropertiesService.findGamePropertiesByPlayer(p);
    }
     @GetMapping("/creator/{gameId}/{playerId}")
    public Boolean findIfIsCreator(@PathVariable("gameId") Integer gameId, @PathVariable("playerId") Integer playerId){
        return gamePropertiesService.findIfIsCreator(gameId,playerId);
    }

    @PutMapping("/gamePoints/{gameId}/player/{playerId}")
    public ResponseEntity<GameProperties> sumPoints(@PathVariable("gameId") Integer gameId, @PathVariable("playerId") Integer playerId){
        return gamePropertiesService.sumPoints(gameId, playerId);
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GameProperties> create(@RequestBody @Valid GameProperties gp) {
        GameProperties gpCreator = new GameProperties();
        BeanUtils.copyProperties(gp,gpCreator, "id");
        User user = userService.findCurrentUser();
        Player creator = userService.findPlayerByUser(user.getId());
        gpCreator.setPlayer(creator);
        gpCreator.setIsCreator(gp.getIsCreator());
        gpCreator.setGamePoints(null);
        gpCreator.setIsGameWinner(false);
        gpCreator.setGame(gp.getGame());
        GameProperties savedCreator;
        savedCreator = this.gamePropertiesService.save(gpCreator);
        gamePropertiesService.save(gpCreator);
        return new ResponseEntity<>(savedCreator, HttpStatus.CREATED);
    }
}