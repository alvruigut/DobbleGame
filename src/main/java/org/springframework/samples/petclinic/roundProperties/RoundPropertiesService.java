package org.springframework.samples.petclinic.roundProperties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoundPropertiesService {
    private RoundPropertiesRepository roundPropertiesRepository;

    @Autowired
    public RoundPropertiesService(RoundPropertiesRepository roundPropertiesRepository){
        this.roundPropertiesRepository=roundPropertiesRepository;
    }

    @Transactional(readOnly = true)
    public List<RoundProperties> findAll(){
        return roundPropertiesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Player> findPlayersByRound(Round r){
        List<RoundProperties> rp = r.getRoundProperties();
        List<Player> players = new ArrayList<>();
        rp.forEach(x->  players.add(x.getPlayer()));
        return players;
    }
    @Transactional
    public RoundProperties save(RoundProperties rp)  throws DataAccessException{
        return roundPropertiesRepository.save(rp);
    }
    

    @Transactional(readOnly = true)
    public RoundProperties findRoundPropertiesByPlayerAndRound(Player p, Round r){
        return roundPropertiesRepository.findPropertiesByPlayerAndRound(p, r);
    }
}
