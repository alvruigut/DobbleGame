package org.springframework.samples.petclinic.roundProperties;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "round_properties")
@Getter
@Setter
public class RoundProperties extends BaseEntity {
    
    @Column(name = "rounds_points")
	@ColumnDefault("0")
	private Integer roundPoints;

    @Column(name = "is_round_winner")
	private Boolean isRoundWinner;

	@Column(name = "accumulated_cards")
	private Integer accumulatedCards;

	@ManyToOne(optional = false)
	@JoinColumn(name = "round_id", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Round round;

	@ManyToOne(optional = false)
	@JoinColumn(name = "player_id", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Player player;

	@ManyToMany
	private List<Card> cards;

	public void addPoints(Integer points) {
		roundPoints+=points;
	}

	
}
