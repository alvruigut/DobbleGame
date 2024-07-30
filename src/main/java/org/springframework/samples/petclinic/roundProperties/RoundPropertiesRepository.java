package org.springframework.samples.petclinic.roundProperties;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;

public interface RoundPropertiesRepository extends CrudRepository<RoundProperties, Integer>{
    
    public List<RoundProperties> findAll();

    @Query("SELECT rp FROM RoundProperties rp WHERE rp.player=?1 AND rp.round=?2")
    public RoundProperties findPropertiesByPlayerAndRound(Player p, Round r);

    @Query("SELECT rp.roundPoints FROM RoundProperties rp WHERE rp.player=?1 AND rp.round=?2")
    public Integer findPlayerPoints(Player p, Round r);
}
