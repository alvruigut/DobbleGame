package org.springframework.samples.petclinic.gameProperties;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;

public interface GamePropertiesRepository extends CrudRepository<GameProperties, Integer> {
    
    List<GameProperties> findAll();
    
    @Query("SELECT gp FROM GameProperties gp WHERE gp.game=?1")
    public GameProperties findGamePropertiesByGame(Game g);

    @Query("SELECT gp FROM GameProperties gp WHERE gp.game=?1")
    public List<GameProperties> findGamePropertiesByGame2(Game g);
    
    @Query("SELECT gp FROM GameProperties gp WHERE gp.player=?1")
    public GameProperties findGamePropertiesByPlayer(Player p);

    @Query("SELECT gp.isCreator FROM GameProperties gp WHERE gp.game=?1 AND gp.player=?2")
    public Boolean findIfIsCreator(Game g, Player p);

    @Query("SELECT gp FROM GameProperties gp WHERE gp.game=?1 AND gp.player=?2")
    public GameProperties findGPByGameAndPlayer(Game g, Player p);

    @Query("SELECT gp.isGameWinner FROM GameProperties gp WHERE gp.player.id = :playerId")
    Boolean getIsGameWinnerByPlayer(@Param("playerId") Integer playerId);
}