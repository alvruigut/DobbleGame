package org.springframework.samples.petclinic.card;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    private CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public Card saveCard(Card card) throws DataAccessException {
        cardRepository.save(card);
        return card;
    }

    @Transactional(readOnly = true)
    public Card findCardById(int id) throws DataAccessException {
        Optional<Card> card = cardRepository.findById(id);
        if(card.isEmpty()) {
            throw new ResourceNotFoundException("Card not found with id" + id);
        }
        return card.get();
    }

    @Transactional(readOnly = true)
    public List<Card> findAllCards() throws DataAccessException {
        return cardRepository.findAll();
    }

    @Transactional
    public void deleteAllCards() throws DataAccessException {
        cardRepository.deleteAll();
    }

    @Transactional
    public void deleteCardById(int id) throws DataAccessException {
        cardRepository.deleteById(id);
    }
}
