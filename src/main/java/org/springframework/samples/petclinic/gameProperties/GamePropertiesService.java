package org.springframework.samples.petclinic.gameProperties;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.roundProperties.RoundPropertiesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GamePropertiesService {
    private GamePropertiesRepository gamePropertiesRepository;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private RoundPropertiesRepository roundPropertiesRepository;


    @Autowired
    public GamePropertiesService(GamePropertiesRepository gamePropertiesRepository, GameRepository gameRepository, PlayerRepository playerRepository, RoundPropertiesRepository roundPropertiesRepository){
        this.gamePropertiesRepository=gamePropertiesRepository;
        this.gameRepository= gameRepository;
        this.playerRepository= playerRepository;
        this.roundPropertiesRepository= roundPropertiesRepository;
    }

    @Transactional(readOnly = true)
    public List<GameProperties> findAll(){
        return gamePropertiesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public GameProperties findGamePropertiesByGame(Game g){
        return gamePropertiesRepository.findGamePropertiesByGame(g);
    }

    @Transactional(readOnly = true)
    public List<GameProperties> findGamePropertiesByGameId(Integer g){
        Game game= gameRepository.findById(g).get();
        return gamePropertiesRepository.findGamePropertiesByGame2(game);
    }

    @Transactional(readOnly = true)
    public GameProperties findGamePropertiesByPlayer(Player p){
        return gamePropertiesRepository.findGamePropertiesByPlayer(p);
    }

    @Transactional(readOnly = true)
    public Boolean findIfIsCreator(Integer gameId,Integer playerId){
        Game game = gameRepository.findById(gameId).get();
        Player player = playerRepository.findById(playerId).get();
        return gamePropertiesRepository.findIfIsCreator(game,player);
    }

    @Transactional
    public GameProperties save(GameProperties gp)  throws DataAccessException{
        return gamePropertiesRepository.save(gp);
    }
    
    @Transactional(readOnly = true)
    public GameProperties findByGameAndPlayer(Integer gameId, Integer playerId){
        Game g= gameRepository.findById(gameId).get();
        Player p= playerRepository.findById(playerId).get();
        return gamePropertiesRepository.findGPByGameAndPlayer(g, p);
    }
    
    @Transactional(readOnly = true)
    public Integer getAllPointsOfPlayerInGame(Integer gameId, Integer playerId){
        Integer points= 0;
        Player p= playerRepository.findById(playerId).get();
        List<Round> rondas= gameRepository.findRoundsByGameId(gameId);
        for (Round round : rondas) {
            points += roundPropertiesRepository.findPlayerPoints(p, round);
        }
        return points;
    }

    @Transactional
	public GameProperties updateGameProperties(GameProperties gp, Integer id) throws DataAccessException {
		GameProperties toUpdate = gamePropertiesRepository.findById(id).get();
		BeanUtils.copyProperties(gp, toUpdate);
		return save(toUpdate);
	}

    @Transactional
    public ResponseEntity<GameProperties> sumPoints(Integer gameId, Integer playerId){
        GameProperties gpUpt= findByGameAndPlayer(gameId, playerId);
        Integer totalPoints= getAllPointsOfPlayerInGame(gameId, playerId);
        gpUpt.setGamePoints(totalPoints);
        save(gpUpt);
        return new ResponseEntity<>(gpUpt, HttpStatus.OK);
    }
}