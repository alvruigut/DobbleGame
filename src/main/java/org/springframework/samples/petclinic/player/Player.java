package org.springframework.samples.petclinic.player;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.samples.petclinic.gameProperties.GameProperties;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;
import org.springframework.samples.petclinic.user.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "players")

public class Player extends Person {

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
    
    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<GameProperties> gameProperties;

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<RoundProperties> roundProperties;    
}
