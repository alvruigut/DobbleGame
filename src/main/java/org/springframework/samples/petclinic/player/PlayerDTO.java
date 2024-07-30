package org.springframework.samples.petclinic.player;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {
    Integer id;
  
	@NotEmpty
	protected String firstName;

	@NotEmpty
	protected String lastName;

	@NotEmpty
	protected String email;

	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	protected String telephone;

    public PlayerDTO(){}

    public PlayerDTO(Player p){
        this.id=p.getId();
        this.firstName=p.getFirstName();
        this.lastName=p.getLastName();
        this.email=p.getEmail();
        this.telephone= p.getTelephone();
    }

}