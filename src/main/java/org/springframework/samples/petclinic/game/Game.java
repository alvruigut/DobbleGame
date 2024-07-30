package org.springframework.samples.petclinic.game;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Check;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.gameProperties.GameProperties;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.round.Round;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "game")
public class Game extends BaseEntity{

    @Column(name = "is_competitive")
    @NotNull
    private Boolean isCompetitive;

    @Column(name = "state")
    @NotNull
    @Enumerated(EnumType.STRING)
    private GameState state;

    @Column(name = "time_limit")
    @Check(constraints = "time_limit IS NULL OR time_limit IN (5, 10)")
    private Integer timeLimit;

    @Size(min = 3, max = 5)
    @Column(name = "game_code")
    private String gameCode;

    @Column(name = "start_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH/mm/ss")
    private LocalDateTime startDate;

    @Column(name = "finish_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH/mm/ss")
    private LocalDateTime finishDateTime;

    private Boolean limitedByRound;

    @Column(name = "round_limit")
    @Min(1)
    @Max(5)
    private Integer roundLimit;

    @JsonIgnore
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GameProperties> gameProperties;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Round> rounds;


    
}