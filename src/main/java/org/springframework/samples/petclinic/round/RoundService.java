package org.springframework.samples.petclinic.round;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardRepository;
import org.springframework.samples.petclinic.card.Deck;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;
import org.springframework.samples.petclinic.roundProperties.RoundPropertiesRepository;
import org.springframework.samples.petclinic.util.HotPotato;
import org.springframework.samples.petclinic.util.InfernoTower;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoundService {
    
    @Autowired
    private RoundRepository rp;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private RoundPropertiesRepository roundPropertiesRepository;

    @Transactional(readOnly = true)
    public List<Round> findAllRounds() throws DataAccessException {
        return rp.findAllRounds();
    }
    
    @Transactional()
    public Round save(Round round) {
        return rp.save(round);
    }

    @Transactional(readOnly = true)
    public Round getRoundById(int id) throws DataAccessException {
        Optional<Round> r = rp.findById(id);
        if(r.isEmpty()) {
            throw new ResourceNotFoundException("Round not found with id:" + id);
        }
        return r.get();
    }
    public List<Player> getPlayersByRound(Integer roundId){
        List<Player> players = new ArrayList<Player>();
        Round r = getRoundById(roundId);
        List<RoundProperties> roundProperties= rp.findPlayersByGame(r);
        for (RoundProperties rp : roundProperties) {
            players.add(rp.getPlayer());
        }
        return players;
    }

    public Game getGame(Integer idRonda){
        return rp.findGameByRoundId(rp.findById(idRonda).get());
    }

    @Transactional()
    public void initialDealOfCards(Integer gameId, Integer roundId) {
        Game game = gameRepository.findById(gameId).get();
        List<Player> players = gameRepository.findPlayersByGame(game);
        Round round = getRoundById(roundId);

        Deck allCards = Deck.of(cardRepository.findAll());
        allCards.shuffleCards();
        Map<Player, Deck> deal = allCards.dealOfCards(players);

        List<Card> centralDeck = allCards.centralDeck(players);
        round.setCentralDeck(centralDeck);
        save(round);

        for(Player p : players) { 
            RoundProperties rp = roundPropertiesRepository.findPropertiesByPlayerAndRound(p, round);
            rp.setCards(deal.get(p));
            roundPropertiesRepository.save(rp);
        }
    } 

    @Transactional()
    public void updateInfernoTower(Integer gameId, Integer roundId, Integer playerId) {
        Round round = getRoundById(roundId);
        Player player = playerRepository.findById(playerId).get();
        Deck centralDeck = Deck.of(round.getCentralDeck());
        RoundProperties rp = roundPropertiesRepository.findPropertiesByPlayerAndRound(player, round);
    
        Card removedCard = centralDeck.remove(0);
        List<Card> newCardsForPlayer = InfernoTower.addCardToPlayer(removedCard, rp);

        Round newRound = new Round();
        BeanUtils.copyProperties(round, newRound);
        newRound.setCentralDeck(centralDeck);
        save(newRound);

        RoundProperties newRoundProperties = new RoundProperties();
        BeanUtils.copyProperties(rp, newRoundProperties);
        newRoundProperties.setCards(newCardsForPlayer);
        roundPropertiesRepository.save(newRoundProperties);
    }

    @Transactional()
    public void updatePointsCountInfernoTower(Integer gameId, Integer roundId) {
        Round round = getRoundById(roundId);
        List<Player> players = getPlayersByRound(roundId);
        Map<Player, Integer> pointsPerPlayer = new HashMap<>();
        
        for(Player p: players) {
            RoundProperties rp = roundPropertiesRepository.findPropertiesByPlayerAndRound(p, round);
            Integer points = InfernoTower.pointsPerPlayer(rp);
            rp.setRoundPoints(points);
            roundPropertiesRepository.save(rp);

            pointsPerPlayer.put(p, points);
        }

        Integer bestPoints = pointsPerPlayer.values().stream().max(Comparator.naturalOrder()).get();
        List<Player> winnerPlayers = pointsPerPlayer.entrySet().stream().filter(es -> es.getValue().equals(bestPoints)).map(es -> es.getKey()).toList();

        for(Player p: winnerPlayers) {
            RoundProperties rp = roundPropertiesRepository.findPropertiesByPlayerAndRound(p, round);
            rp.setRoundPoints(rp.getRoundPoints() + 5);
            rp.setIsRoundWinner(true);
            roundPropertiesRepository.save(rp);
        }
    }

    @Transactional()
    public void nextDealOfCardsHotPotato(Integer gameId,Integer roundId) {
        Round round = getRoundById(roundId);
        Game game = gameRepository.findById(gameId).get();
        List<Player> players = gameRepository.findPlayersByGame(game);
            
        Deck centralDeck = Deck.of(round.getCentralDeck());

        Map<Player, Deck> deal = centralDeck.dealOfCards(players);

        for(Player p : players) { 
            centralDeck.remove(0);
            RoundProperties rp= roundPropertiesRepository.findPropertiesByPlayerAndRound(p, round);
            rp.setCards(deal.get(p));
            roundPropertiesRepository.save(rp);
        }
        round.setCentralDeck(centralDeck);
        save(round);
    }

    @Transactional()
    public void updateHotPotato(Integer gameId, Integer roundId, Integer playerId1, Integer playerId2) {
        Player player1 = playerRepository.findById(playerId1).get();
        Player player2 = playerRepository.findById(playerId2).get();
        Round round = getRoundById(roundId);
        RoundProperties rp1 = roundPropertiesRepository.findPropertiesByPlayerAndRound(player1, round);
        RoundProperties rp2 = roundPropertiesRepository.findPropertiesByPlayerAndRound(player2, round);
        List<Card> cardsPlayer1 = rp1.getCards(); 
        List<Card> cardsPlayer2 = rp2.getCards(); 
    
        cardsPlayer2.addAll(0, cardsPlayer1);
        rp1.setCards(new ArrayList<Card>());
        rp2.setCards(cardsPlayer2);
        roundPropertiesRepository.save(rp1);
        roundPropertiesRepository.save(rp2);
    }

    @Transactional()
    public void updatePointsHotPotato(Integer roundId, Integer gameId){
        Round round = getRoundById(roundId);
        Game game = gameRepository.findById(gameId).get();
        List<Player> players = gameRepository.findPlayersByGame(game);
        for(Player p: players){
            RoundProperties rp = roundPropertiesRepository.findPropertiesByPlayerAndRound(p, round);
            Integer numCartas = rp.getCards().size();
            if(numCartas == players.size()){
                Integer acCards = rp.getAccumulatedCards();
                rp.setAccumulatedCards(numCartas + acCards);
                roundPropertiesRepository.save(rp);
            }
        }
        save(round);
    }

    @Transactional()
    public void updatePointsCountHotPotato(Integer gameId, Integer roundId) {
        Round round = getRoundById(roundId);
        List<Player> players = getPlayersByRound(roundId);
        Map<Player, Integer> pointsPerPlayer = new HashMap<>();
        
        for(Player p: players) {
            RoundProperties rp = roundPropertiesRepository.findPropertiesByPlayerAndRound(p, round);
            Integer points = HotPotato.pointsPerPlayer(rp);
            rp.setRoundPoints(points);
            roundPropertiesRepository.save(rp);

            pointsPerPlayer.put(p, points);
        }

        Integer bestPoints = pointsPerPlayer.values().stream().max(Comparator.naturalOrder()).get();
        List<Player> winnerPlayers = pointsPerPlayer.entrySet().stream().filter(es -> es.getValue().equals(bestPoints)).map(es -> es.getKey()).toList();

        for(Player p: winnerPlayers) {
            RoundProperties rp = roundPropertiesRepository.findPropertiesByPlayerAndRound(p, round);
            rp.setIsRoundWinner(true);
            roundPropertiesRepository.save(rp);
        }
    }

}
