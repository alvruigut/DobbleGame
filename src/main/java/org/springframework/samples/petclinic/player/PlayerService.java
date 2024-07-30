package org.springframework.samples.petclinic.player;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.gameProperties.GameProperties;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {
        
    private PlayerRepository playerRepository;

    @Autowired
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

    @Transactional(readOnly = true)
	public List<Player> findAll() throws DataAccessException{
		return playerRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Player getPlayerById(Integer id) throws DataAccessException {
		Optional<Player> p = playerRepository.findById(id);
		if(p.isEmpty()) {
			throw new ResourceNotFoundException("Player not found with id:" + id);
		}
		return p.get();
	}

    @Transactional(readOnly = true)
	public Player findPlayerByUserId(int userId) {
		return this.playerRepository.findByUserId(userId);
	}

	@Transactional(readOnly = true)
	public Player findPlayerByUsername(String username) {
		return this.playerRepository.findByUsername(username);
	}

 	@Transactional(readOnly = true)
    public List<Game> findGamesByPlayer(Player p){
        List<GameProperties> gpList = playerRepository.findGamesByPlayer(p);
        List<Game> games = new ArrayList<>();
        for(int i=0; i<gpList.size(); i++ ){
            games.add(gpList.get(i).getGame());
        }
        return games;
    }

	@Transactional(readOnly = true)
    public List<Round> findRoundsByPlayer(Player p){
        List<RoundProperties> rpList = playerRepository.findRoundsByPlayer(p);
        List<Round> rounds = new ArrayList<>();
        for(int i=0; i<rpList.size(); i++ ){
            rounds.add(rpList.get(i).getRound());
        }
        return rounds;
    }

	@Transactional
	public Player savePlayer(Player player) {
		playerRepository.save(player);
		return player;
	}

	@Transactional
	public void deletePlayerByPlayerId(int id) throws Exception {
		Player toDelete = getPlayerById(id);
		deletePlayer(toDelete);
	}

	@Transactional
	public void deletePlayer(Player p) throws Exception {
		playerRepository.delete(p);
	}

	@Transactional
	public Player updatePlayer(Player player , int id) throws DataAccessException {
		Player toUpdate = getPlayerById(id);
		BeanUtils.copyProperties(player, toUpdate, "id", "user");
		return savePlayer(toUpdate);
	}

}