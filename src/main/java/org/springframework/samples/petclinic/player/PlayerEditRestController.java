package org.springframework.samples.petclinic.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/profile")
public class PlayerEditRestController {
    
    private final PlayerService playerService;
	private final UserService userService;

    @Autowired
    public PlayerEditRestController(PlayerService ps, UserService us) {
        this.playerService=ps;
		this.userService=us;
    }

    @PutMapping()
	public void updatePlayerById(@RequestBody @Valid PlayerDTO player) {
		Player existingPlayer = playerService.getPlayerById(player.getId());
		if(existingPlayer == null) {
			throw new ResourceNotFoundException("No existe el jugador con id: " + player.getId());
		}
		String firstName = player.getFirstName();
		String lastName = player.getLastName();
		String email = player.getEmail();
		String telephone = player.getTelephone();

		existingPlayer.setFirstName(firstName);
		existingPlayer.setLastName(lastName);
		existingPlayer.setEmail(email);
		existingPlayer.setTelephone(telephone);

		playerService.savePlayer(existingPlayer);
	}

	@GetMapping()
	public PlayerDTO findCurrentPlayer() {
		User user = userService.findCurrentUser();
		Player p = playerService.findPlayerByUserId(user.getId());
		return new PlayerDTO(p);
	}

	@GetMapping("/player")
	public Player findCurrentPlayerNoDTO() {
		User user = userService.findCurrentUser();
		Player p = playerService.findPlayerByUserId(user.getId());
		return p;
	}
}