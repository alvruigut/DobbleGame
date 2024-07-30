package org.springframework.samples.petclinic.round;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;

public interface RoundRepository extends CrudRepository<Round, Integer>{

    @Query("SELECT round FROM Round round")
    public List<Round> findAllRounds();

    @Query("SELECT rp FROM RoundProperties rp WHERE rp.round=?1")
    public List<RoundProperties> findPlayersByGame(Round r);

    @Query("SELECT g FROM Game g WHERE ?1 MEMBER OF g.rounds")
    public Game findGameByRoundId(Round ronda);
}