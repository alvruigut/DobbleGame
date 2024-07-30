package org.springframework.samples.petclinic.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.samples.petclinic.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Deck extends ArrayList<Card> {

    public static Deck of(List<Card> cards){
        Deck deck = new Deck();
        deck.addAll(cards);
        return deck;
    }

    private static Deck of(Card card) {
        Deck deck = new Deck();
        deck.add(card);
        return deck;
    }
    
    public void shuffleCards(){
       Collections.shuffle(this);
    }

    public Map<Player, Deck> dealOfCards(List<Player> players) {
        Map<Player,Deck> cardsPlayer = new HashMap<>();
        for(Player p : players){ 
            int i = players.indexOf(p);
            cardsPlayer.put(p, Deck.of(this.get(i)));
		}
        return cardsPlayer;
    }

    public List<Card> centralDeck(List<Player> players) {
        return this.subList(players.size(), this.size());

    }
}
