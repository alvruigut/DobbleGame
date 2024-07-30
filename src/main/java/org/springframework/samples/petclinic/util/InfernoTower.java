package org.springframework.samples.petclinic.util;

import java.util.List;

import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfernoTower {

    public InfernoTower infernoTower() {
        InfernoTower infernoTower = new InfernoTower();
        return infernoTower;
    }

    public static List<Card> addCardToPlayer(Card c, RoundProperties rp) {
        List<Card> cards = rp.getCards();
        cards.add(0, c);
        return cards;
    }

    //actualizar los puntos del roundProperties al terminar la ronda: +1 punto por cada carta
    public static Integer pointsPerPlayer(RoundProperties rp) {
        Integer points = rp.getCards().size();
        return points;
    }


}
