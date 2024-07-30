package org.springframework.samples.petclinic.roundProperties;

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
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "RoundProperties", description = "The RoundProperties management API")
@SecurityRequirement(name = "bearerAuth")
public class RoundPropertiesController {

    private final RoundPropertiesService roundPropertiesService;
    private final RoundService roundService;
    private final PlayerService playerService;


    @Autowired
    public RoundPropertiesController(RoundPropertiesService roundPropertiesService, RoundService roundService, 
                                        PlayerService playerService){
        this.roundPropertiesService=roundPropertiesService;
        this.roundService= roundService;
        this.playerService=playerService;
    }

    @GetMapping("/roundProperties")
    public ResponseEntity<List<RoundProperties>> getAll(){
        return new ResponseEntity<>((List<RoundProperties>) roundPropertiesService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/roundProperties/rounds/{round}")
    public List<Player> findPlayersByRound(@PathVariable("round") Round r){
        return roundPropertiesService.findPlayersByRound(r);
    }

    
    @PostMapping("/roundProperties")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RoundProperties> create(@RequestBody @Valid RoundProperties rp) {
        RoundProperties newRp= new RoundProperties();
        BeanUtils.copyProperties(rp,newRp, "id");
        List<Card> cardList = new ArrayList<>();
        newRp.setCards(cardList);
        newRp.setIsRoundWinner(false);
        newRp.setPlayer(rp.getPlayer());
        newRp.setRound(rp.getRound());
        newRp.setRoundPoints(0);
        newRp.setAccumulatedCards(0);
        RoundProperties savedRoundProperties;
        savedRoundProperties = this.roundPropertiesService.save(newRp);
        return new ResponseEntity<>(savedRoundProperties, HttpStatus.CREATED);
    }


    @GetMapping("/rounds/{roundId}/players/{playerId}/roundProperties")
    public ResponseEntity<RoundProperties> findRpByRoundAndPlayer(@PathVariable("roundId") int roundId, 
    @PathVariable("playerId") int playerId){
        Round r = roundService.getRoundById(roundId);
        Player p = playerService.getPlayerById(playerId);
        if(r == null) {
            throw new ResourceNotFoundException("No encuentro la ronda con id: " + roundId);
        }
        if(p == null) {
            throw new ResourceNotFoundException("No encuentro el jugador con id: " + playerId);
        }

        RoundProperties rp = roundPropertiesService.findRoundPropertiesByPlayerAndRound(p, r);

        if(rp == null) {
			throw new ResourceNotFoundException("No existe el roundProperties con jugador de id " + playerId 
            + " y ronda de id " + roundId);
		}
        return new ResponseEntity<>(rp, HttpStatus.OK);
    }
    
}