package org.springframework.samples.petclinic.game;

import java.util.List;

import org.hibernate.annotations.Check;
import org.springframework.samples.petclinic.round.Round;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {

    Integer id;

    @NotNull
    Boolean isCompetitive;

    @NotNull
    @NotEmpty
    String state;

    @Check(constraints = "time_limit IS NULL OR time_limit IN (5, 10, 15)")
    Integer timeLimit;

    Boolean limitedByRound;

    @Min(1)
    @Max(5)
    Integer roundLimit;

    List<Round> rounds;

    public GameDTO() {

    }

    public GameDTO(Game g) {
        this.id = g.getId();
        this.isCompetitive = g.getIsCompetitive();
        this.state = g.getState().toString();
        this.timeLimit = g.getTimeLimit();
        this.limitedByRound = g.getLimitedByRound();
        this.roundLimit = g.getRoundLimit();
        this.rounds = g.getRounds();
    }

}