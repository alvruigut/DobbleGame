package org.springframework.samples.petclinic.player;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.gameProperties.GameProperties;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;

public interface PlayerRepository extends CrudRepository<Player, Integer>{

    List<Player> findAll();

    @Query("SELECT DISTINCT player FROM Player player WHERE player.user.id = :userId")
	public Player findByUserId(int userId);

    @Query("SELECT player FROM Player player WHERE player.user.username = :username")
    public Player findByUsername(String username);

    @Query("SELECT gp FROM GameProperties gp WHERE gp.player=?1")
    public List<GameProperties> findGamesByPlayer(Player p);

    @Query("SELECT rp FROM RoundProperties rp WHERE rp.player=?1")
    public List<RoundProperties> findRoundsByPlayer(Player p);

}