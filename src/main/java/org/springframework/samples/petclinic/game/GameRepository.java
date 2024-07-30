package org.springframework.samples.petclinic.game;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;


public interface GameRepository extends CrudRepository<Game, Integer> {

    public List<Game> findAll();

    @Query("SELECT g FROM Game g WHERE g.state=?1")
    public List<Game> findAllByState(GameState state);

    @Query("SELECT g.rounds FROM Game g WHERE g.id=?1")
    public List<Round> findRoundsByGameId(Integer id);

    @Query("SELECT g FROM Game g JOIN g.gameProperties gp JOIN gp.player p JOIN p.user u WHERE u.username = ?1")
    public List<Game> findByUserName( String username);

    // Encontrar lista de juegos por id de usuario
    @Query("SELECT g FROM Game g JOIN g.gameProperties gp JOIN gp.player p JOIN p.user u WHERE u.id = ?1")
    public List<Game> findByUserId( Integer id);

    @Query("SELECT g FROM Game g WHERE g.gameCode = ?1")
    public Game findGameByCode(String code);

    @Query("SELECT gp.player FROM GameProperties gp WHERE gp.game=?1")
    public List<Player> findPlayersByGame(Game g );

}