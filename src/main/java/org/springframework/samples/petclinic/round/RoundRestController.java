package org.springframework.samples.petclinic.round;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;
import org.springframework.samples.petclinic.roundProperties.RoundPropertiesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
@SecurityRequirement(name = "bearerAuth")

public class RoundRestController {
    
    private final PlayerService playerService;
	private final RoundService roundService;
    private final RoundPropertiesService roundPropertiesService;

    @Autowired
    public RoundRestController(PlayerService playerService, RoundService roundService, RoundPropertiesService roundPropertiesService) {
        this.playerService = playerService;
		this.roundService = roundService;
        this.roundPropertiesService = roundPropertiesService;
    }
    @Autowired
    private RoundService rs;

    @GetMapping("/rounds")
    public List<Round> findAll(){
        return rs.findAllRounds();
    }

    @PostMapping("/rounds")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Round> createRound(@RequestBody @Valid Round round){
        Round newRound = new Round();
        BeanUtils.copyProperties(round, newRound, "id");
        List<Card> cards = new ArrayList<>();
        newRound.setCentralDeck(cards);
        List<RoundProperties> rp = new ArrayList<>();
        newRound.setRoundProperties(rp);
        newRound.setIsOnGoing(round.getIsOnGoing());
        newRound.setMinigame(round.getMinigame());
        Round savedRound;
        savedRound = this.roundService.save(newRound);
        return new ResponseEntity<>(savedRound, HttpStatus.CREATED);
    }

    @PutMapping("/startRound/rounds/{roundId}")
    public void startRound(@PathVariable("roundId") Integer roundId){
        Round round = roundService.getRoundById(roundId);
        if (round == null) {
            throw new ResourceNotFoundException("No existe la ronda con id: " + roundId);
        }
        round.setIsOnGoing(true);
        roundService.save(round);
    }

    @PutMapping("/finishRound/rounds/{roundId}")
    public void finishRound(@PathVariable("roundId") Integer roundId){
        Round round = roundService.getRoundById(roundId);
        if (round == null) {
            throw new ResourceNotFoundException("No existe la ronda con id: " + roundId);
        }
        round.setIsOnGoing(false);
        roundService.save(round);
    }

    @GetMapping("/games/{id}/rounds/{roundCreatedId}")
    public ResponseEntity<Round> findById(@PathVariable("roundCreatedId") int roundCreatedId, @PathVariable("id") int id) {
        Round r = rs.getRoundById(roundCreatedId);
        if (r == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PutMapping("/games/{gameId}/rounds/{roundId}/players/initialDeal") 
    public void initialDealOfCards(@PathVariable("gameId") Integer gameId,
    @PathVariable("roundId") Integer roundId) {
        roundService.initialDealOfCards(gameId, roundId);
    }

    @PutMapping("/games/{gameId}/rounds/{roundId}/players/nextDeal") 
    public void nextDealOfCardsHotPotato(@PathVariable("gameId") Integer gameId,
    @PathVariable("roundId") Integer roundId) {
        roundService.nextDealOfCardsHotPotato(gameId, roundId);
    }
    
    @PutMapping("/games/{gameId}/rounds/{roundId}/players/{playerId}/infernoTower")
    public void updateInfernoTower(@PathVariable("gameId") Integer gameId,
    @PathVariable("roundId") Integer roundId, @PathVariable("playerId") Integer playerId) {
        roundService.updateInfernoTower(gameId, roundId, playerId);
    }

    @PutMapping("/games/{gameId}/rounds/{roundId}/pointsPlayerInfernoTower")
    public void updatePointsCountInfernoTower(@PathVariable("gameId") Integer gameId,
    @PathVariable("roundId") Integer roundId) {
        roundService.updatePointsCountInfernoTower(gameId, roundId);
    }

    @PutMapping("/games/{gameId}/rounds/{roundId}/pointsPlayerInfernoTower/players/{playerId}")
    public void updatePointsCountInfernoTower(@PathVariable("gameId") Integer gameId,
    @PathVariable("roundId") Integer roundId, @PathVariable("playerId") Integer playerId){
        Player player = playerService.getPlayerById(playerId);
        Round round = roundService.getRoundById(roundId);
        RoundProperties rp = roundPropertiesService.findRoundPropertiesByPlayerAndRound(player, round);
        rp.addPoints(1);
        roundPropertiesService.save(rp);
    }


    @PutMapping("/games/{gameId}/rounds/{roundId}/players/{playerId1}/{playerId2}/hotPotato")
    public void updateHotPotato(@PathVariable("gameId") Integer gameId,
    @PathVariable("roundId") Integer roundId, @PathVariable("playerId1") Integer playerId1, 
    @PathVariable("playerId2") Integer playerId2) {
        roundService.updateHotPotato(gameId, roundId, playerId1, playerId2);
    }

    @PutMapping("/games/{gameId}/rounds/{roundId}/hotPotatoPoints")
    public void updatePointsHotPotato(@PathVariable("gameId") Integer gameId,
    @PathVariable("roundId") Integer roundId) {
        roundService.updatePointsHotPotato(roundId, gameId);
    }

    @PutMapping("/games/{gameId}/rounds/{roundId}/pointsPlayerHotPotato")
    public void updatePointsCounHotPotato(@PathVariable("gameId") Integer gameId,
    @PathVariable("roundId") Integer roundId) {
        roundService.updatePointsCountHotPotato(gameId, roundId);
    }

}