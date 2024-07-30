package org.springframework.samples.petclinic.round;

import java.util.List;

import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Round extends BaseEntity {
    
    @Column(name = "minigame")
	@NotNull
    @Enumerated(EnumType.STRING)
    private Minigame minigame;

    @Column(name = "is_on_going")
	@NotNull
    private Boolean isOnGoing;

    //@JsonIgnore
    @Column(name = "central_deck")
    @ManyToMany
    private List<Card> centralDeck;

    @JsonIgnore
    @OneToMany(mappedBy = "round")
    private List<RoundProperties> roundProperties;
}
