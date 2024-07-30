package org.springframework.samples.petclinic.gameProperties;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "game_properties")
@Getter
@Setter
public class GameProperties extends BaseEntity {

    @Column(name = "game_points")
    @ColumnDefault("0")
    private Integer gamePoints;

    @Column(name = "is_game_winner")
    private Boolean isGameWinner;

    @NotNull
    @Column(name = "is_creator")
    private Boolean isCreator;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Game game;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Player player;

}