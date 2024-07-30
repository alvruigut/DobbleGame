package org.springframework.samples.petclinic.player;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.auth.payload.response.MessageResponse;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.util.RestPreconditions;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/v1/players")
@SecurityRequirement(name = "bearerAuth")
public class PlayerRestController {

    private final PlayerService playerService;
	private final UserService userService;

    @Autowired
    public PlayerRestController(PlayerService playerService, UserService userService) {
        this.playerService = playerService;
        this.userService = userService;
    }

    @GetMapping
	public List<Player> findAll() {
		return playerService.findAll();
	}

    @GetMapping(value = "{playerId}")
	public Player findById(@PathVariable("playerId") int id) {
		Player p = playerService.getPlayerById(id);
		if(p == null) {
			throw new ResourceNotFoundException("No existe el jugador con id: " + id);
		}
		return p;
	}

	@GetMapping("/{playerId}/gamesPlayed")
	public List<Game> gamesPlayedBy(@PathVariable("playerId") int id){
		Player p = playerService.getPlayerById(id);
		return playerService.findGamesByPlayer(p);
	}

	@GetMapping("/gamesPlayed")
	public List<Game> gamesPlayedBy(){
		User user = userService.findCurrentUser();
		Player p = userService.findPlayerByUser(user.getId());
		return playerService.findGamesByPlayer(p);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Player> create(@RequestBody @Valid Player player) throws URISyntaxException {
		Player newPlayer = new Player();
		BeanUtils.copyProperties(player, newPlayer, "id");
		User user = userService.findCurrentUser();
		newPlayer.setUser(user);
		Player savedPlayer = this.playerService.savePlayer(newPlayer);

		return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "{playerId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<MessageResponse> delete(@PathVariable("playerId") int id) throws Exception {
		RestPreconditions.checkNotNull(playerService.getPlayerById(id), "Player", "ID", id);
		playerService.deletePlayerByPlayerId(id);
		return new ResponseEntity<>(new MessageResponse("Player deleted!"), HttpStatus.OK);
	}

	@PutMapping(value = "{playerId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Player> update(@PathVariable("playerId") int playerId, @RequestBody @Valid Player player) {
		RestPreconditions.checkNotNull(playerService.getPlayerById(playerId), "Player", "ID", playerId);
		return new ResponseEntity<>(this.playerService.updatePlayer(player, playerId), HttpStatus.OK);
	}
}
