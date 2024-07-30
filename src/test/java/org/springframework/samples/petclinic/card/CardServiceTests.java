package org.springframework.samples.petclinic.card;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.symbol.Symbol;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase

public class CardServiceTests {

    @Autowired
    private CardService cardService;

    @Test
    @Transactional
    void shouldInsertCard() {
        List<Card> cards = this.cardService.findAllCards();
        int cardsFound = cards.size();

        Card card = new Card(98);
        this.cardService.saveCard(card);
        assertNotEquals(card.getId(), 0);

        cards = this.cardService.findAllCards();
        assertEquals(cards.size(), cardsFound + 1);
    }

    @Test
    public void shouldSaveCard() {
        Card card = new Card();
        Symbol a = new Symbol();
        Symbol b = new Symbol();
        a.setId(1);
        a.setName("Caballito Del Cielo");
        b.setId(2);
        b.setName("Caballito De Tierra");
        card.setSymbols(Arrays.asList(a, b));

        Card savedCard = cardService.saveCard(card);
        assertNotNull(savedCard.getId());

        assertEquals(2, savedCard.getSymbols().size());
    }

    @Test
    public void shouldDeleteCardById() {
        Card card = new Card();
        card.setId(10);
        cardService.saveCard(card);

        Integer cardId = card.getId();
        cardService.deleteCardById(cardId);

        assertThrows(ResourceNotFoundException.class, () -> cardService.findCardById(cardId));
    }

    @Test
    public void shouldDeleteAllCards() {
        Card card1 = new Card();
        Card card2 = new Card();
        Card card3 = new Card();
        cardService.saveCard(card1);
        cardService.saveCard(card2);
        cardService.saveCard(card3);

        cardService.deleteAllCards();

        List<Card> allCards = cardService.findAllCards();
        assertEquals(0, allCards.size());
    }


}
