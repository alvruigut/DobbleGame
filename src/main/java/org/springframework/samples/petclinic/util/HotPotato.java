package org.springframework.samples.petclinic.util;

import java.util.List;

import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class HotPotato {
    
    public HotPotato hotPotato(){
        HotPotato hotPotato = new HotPotato();
        return hotPotato;
    }

    public static void finishMiniround(List<Player> players, Round r){
        for(Player p: players){
            List<RoundProperties> rPlayer= p.getRoundProperties();
            RoundProperties rProp = rPlayer.stream().filter(rp -> rp.getRound().equals(r)).findFirst().get();
            if(rProp.getCards().size()==players.size()){
                Integer acCards = rProp.getAccumulatedCards();
                rProp.setAccumulatedCards(rProp.getCards().size()+ acCards);
            }
        }
    }

    public static Integer pointsPerPlayer(RoundProperties rp) {
        Integer points = rp.getAccumulatedCards();
        return -points;
    }
}
