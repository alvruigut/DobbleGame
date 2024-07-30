package org.springframework.samples.petclinic.card;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.auth.payload.response.MessageResponse;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.symbol.Symbol;
import org.springframework.samples.petclinic.symbol.SymbolService;
import org.springframework.samples.petclinic.util.RestPreconditions;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/cards")
public class CardRestController {

    private final CardService cardService;
    private final SymbolService symbolService;

    @Autowired
    public CardRestController(CardService cardService, SymbolService symbolService) {
        this.cardService = cardService;
        this.symbolService = symbolService;
    }

    @GetMapping()
    public @ResponseBody List<Card> getAllCards() {
        return cardService.findAllCards();
    }

    @GetMapping(value = "{cardId}")
    public @ResponseBody Card getCardById(@PathVariable("cardId") int id) {
        Card c = cardService.findCardById(id);
        if(c == null) {
            throw new ResourceNotFoundException("No existe la carta con id" + id);
        }
        return c;
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteAllCards() {
        this.cardService.deleteAllCards();
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Cards deleted");
    }

    @DeleteMapping(value = "{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> deleteCard(@PathVariable("cardId") int id) throws Exception {
        RestPreconditions.checkNotNull(cardService.findCardById(id), "Card", "ID", id);
        cardService.deleteCardById(id);
        return new ResponseEntity<>(new MessageResponse("Card deleted!"), HttpStatus.OK);
    }

    @PostMapping()
    public @ResponseBody Card addNewCard(@RequestBody Card card) {
        card.setSymbols(new ArrayList<Symbol>());
        try {
            return this.cardService.saveCard(card);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    //Método que le entre un symbol y dos cartas. Hay que buscar que el símbolo esté en las dos cartas

    // @GetMapping()
    // public ModelAndView showCards() {
    //     List<Card> cartas = cardService.findAllCards();
    //     ModelAndView resultado = new ModelAndView("cards/" + "ListadoCartas");
    //     resultado.addObject("cards", cartas);
    //     return resultado;
    // }

    // @GetMapping(value = "/create")
    // public ModelAndView createCard() {
    //     ModelAndView resultado = new ModelAndView("cards/" + "EditarCarta");
    //     resultado.addObject("card", new Card());
    //     resultado.addObject("allSymbols", symbolService.findAllSymbols());
    //     return resultado;
    // }

    
}
