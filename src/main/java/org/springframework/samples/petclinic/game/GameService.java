package org.springframework.samples.petclinic.game;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.gameProperties.GameProperties;
import org.springframework.samples.petclinic.gameProperties.GamePropertiesRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private GameRepository gameRepository;
    private UserRepository userRepository;
    private PlayerRepository pr;
    private GamePropertiesRepository gamePropertiesRepository;

    @Autowired
    public GameService(GameRepository gameRepository,UserRepository userRepository,PlayerRepository pr, GamePropertiesRepository gamePropertiesRepository) {
        this.gameRepository = gameRepository;
        this.userRepository=userRepository;
        this.pr=pr;
        this.gamePropertiesRepository=gamePropertiesRepository;
    }

    @Transactional(readOnly = true)
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Game findById(int id) throws DataAccessException {
        Optional<Game> g = gameRepository.findById(id); 
        return g.get();
    }

    @Transactional(readOnly = true)
    public List<Round> findRoundsById(Integer Id) {
        return gameRepository.findRoundsByGameId(Id);
    }

    @Transactional(readOnly = true)
    public List<Game> findByUsername(String username) throws DataAccessException {
        return gameRepository.findByUserName(username);
    }

    @Transactional(readOnly = true)
    public List<Game> findByUserId(Integer id) throws DataAccessException {
        return gameRepository.findByUserId(id);
    }
    
    @Transactional(readOnly = true)
    public List<Player> findPlayersByGame(Game game) {
        return gameRepository.findPlayersByGame(game);
    }

    @Transactional(readOnly = true)
    public Game findGameByGameCode(String code){
        Game g = gameRepository.findGameByCode(code);
        return g;
    }

    @Transactional(readOnly = true)
    public List<Game> findAllByState(GameState state) throws DataAccessException {
        return gameRepository.findAllByState(state);
    }


    @Transactional(readOnly = true)
    public GameState findStateByGameId(Integer id) throws DataAccessException {
        Game g = gameRepository.findById(id).get();
        return g.getState();
    }

    @Transactional
    public Game save(Game game) throws DataAccessException {
        return gameRepository.save(game);
    }

    @Transactional
	public Game updateGame(Game game, int id) throws DataAccessException {
		Game toUpdate = findById(id);
		BeanUtils.copyProperties(game, toUpdate);
		return save(toUpdate);
	}

    @Transactional
    public Integer maxGamePoints(Game g, List<Player> players){
        Integer maxPoints=0;
        for(Player p:players){
        GameProperties gp = gamePropertiesRepository.findGPByGameAndPlayer(g, p);
        Integer gamePoints=gp.getGamePoints();
        if(gamePoints<0){
            gp.setGamePoints(0);
            gamePropertiesRepository.save(gp);
        }else if(gamePoints>=maxPoints && gamePoints>0){
            maxPoints=gamePoints;
        }
        }
        return maxPoints;
    }

    @Transactional
    public void setGameWinner(Integer gameId){
        Game game = findById(gameId);
        List<Player> players = findPlayersByGame(game);
        Integer pointsWinner = maxGamePoints(game, players);

        for(Player player: players){
            GameProperties gp = gamePropertiesRepository.findGPByGameAndPlayer(game, player);
            Integer gamePoints=gp.getGamePoints();
            if(pointsWinner==gamePoints){
                gp.setIsGameWinner(true);
                gamePropertiesRepository.save(gp);
            }
        }
    }

    @Transactional(readOnly = true)
    public Integer countGamesByUserName(Integer u) throws DataAccessException {
        String userName= userRepository.findById(u).get().getUsername();
        return findByUsername(userName).size();
    }

    
   @Transactional(readOnly = true)
     public Double timePlayedByUserName(Integer u) throws DataAccessException {
        String userName= userRepository.findById(u).get().getUsername();
    List<Double> timePlayedForGamesByPlayer= new ArrayList<>();
    List<Game> games= findByUsername(userName);
    for (Game game : games) {
        Double tiempo= ChronoUnit.MINUTES.between(game.getStartDate(), game.getFinishDateTime())+0.;
        timePlayedForGamesByPlayer.add(tiempo);
    }
     Double res= timePlayedForGamesByPlayer.stream().mapToDouble(Double::doubleValue).sum();
     return res;
     }  

     //Tiempo medio jugado
     @Transactional(readOnly = true)
        public Double averageTimePlayedByUserName(Integer u) throws DataAccessException {
            String userName= userRepository.findById(u).get().getUsername();
            List<Double> timePlayedForGamesByPlayer= new ArrayList<>();
            List<Game> games= findByUsername(userName);
            for (Game game : games) {
                Double tiempo= ChronoUnit.MINUTES.between(game.getStartDate(), game.getFinishDateTime())+0.;
                timePlayedForGamesByPlayer.add(tiempo);
            }
            Double res= timePlayedForGamesByPlayer.stream().mapToDouble(Double::doubleValue).sum();
            return res/games.size();
        }

        //Tiempo máximo jugado
        @Transactional(readOnly = true)
        public Double maxTimePlayedByUserName(Integer u) throws DataAccessException {
            String userName= userRepository.findById(u).get().getUsername();
            List<Double> timePlayedForGamesByPlayer= new ArrayList<>();
            List<Game> games= findByUsername(userName);
            for (Game game : games) {
                Double tiempo= ChronoUnit.MINUTES.between(game.getStartDate(), game.getFinishDateTime())+0.;
                timePlayedForGamesByPlayer.add(tiempo);
            }
            Double res= timePlayedForGamesByPlayer.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
            return res;
        }

        //Tiempo mínimo jugado
        @Transactional(readOnly = true)
        public Double minTimePlayedByUserName(Integer u) throws DataAccessException {
            String userName= userRepository.findById(u).get().getUsername();
            List<Double> timePlayedForGamesByPlayer= new ArrayList<>();
            List<Game> games= findByUsername(userName);
            for (Game game : games) {
                Double tiempo= ChronoUnit.MINUTES.between(game.getStartDate(), game.getFinishDateTime())+0.;
                timePlayedForGamesByPlayer.add(tiempo);
            }
            Double res= timePlayedForGamesByPlayer.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
            return res;
        }



    @Transactional(readOnly = true)
    public Integer countCompetitiveGamesByUserName(Integer u) throws DataAccessException {
       String userName= userRepository.findById(u).get().getUsername();
        Integer numCompetitivos = findByUsername(userName).stream().filter(g -> g.getIsCompetitive()).toList().size();
        return numCompetitivos;
    }

    @Transactional(readOnly = true)
    public Integer countNotCompetitiveGamesByUserName(Integer u) throws DataAccessException {
        String userName= userRepository.findById(u).get().getUsername();
        Integer numNotCompetitivos = findByUsername(userName).stream().filter(g -> !g.getIsCompetitive()).toList().size();
        return numNotCompetitivos;
    }


    private GameProperties gProperties(Game g, String userName){
        return g.getGameProperties().stream().filter(gp -> gp.getPlayer().getUser().getUsername().equals(userName)).findFirst().get();
    }
    @Transactional(readOnly = true)
    public Integer countVictoriesRankedByUserName(Integer u) throws DataAccessException {
        Integer numVic =0 ;
        String userName= userRepository.findById(u).get().getUsername();
        List<Game> numRankeds = findByUsername(userName).stream().filter(g -> g.getIsCompetitive()).toList();
        for(Game g:numRankeds){
            if(gProperties(g,userName).getIsGameWinner()){
                numVic++;
            }
        }
        return numVic;
    }

    @Transactional(readOnly = true)
    public Integer countVictoriesFriendlyByUserName(Integer u) throws DataAccessException {
        Integer numVic =0 ;
        String userName= userRepository.findById(u).get().getUsername();
        List<Game> numGames = findByUsername(userName).stream().filter(g -> !g.getIsCompetitive()).toList();
        for(Game g:numGames){
            if(gProperties(g,userName).getIsGameWinner()){
                numVic++;
            }
        }
        return numVic;
    }


    @Transactional(readOnly = true)
    public Map<String, Integer> usuariosXvictoriasRanked() throws DataAccessException {
        Map<String, Integer> res = new HashMap<>();
        Iterable<Player> users = pr.findAll();
        
        for (Player user : users) {
            res.put(user.getUser().getUsername(), countVictoriesRankedByUserName(user.getUser().getId()));
        }

        List<Map.Entry<String, Integer>> list = new LinkedList<>(res.entrySet());

        list.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));

        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Boolean juegaTuPrimeraPartida(Integer u) throws DataAccessException {
        String userName= userRepository.findById(u).get().getUsername();
        List<Game> games= findByUsername(userName);
        if(games.size()==0){
            return false;
        }
        return true;
    }

    @Transactional(readOnly = true)
    public Boolean juega5partidas(Integer u) throws DataAccessException {
        String userName= userRepository.findById(u).get().getUsername();
        List<Game> games= findByUsername(userName);
        if(games.size()<5){
            return false;
        }
        return true;
    }

    @Transactional(readOnly = true)
    public Boolean ganaPrimeraPartida(Integer u) throws DataAccessException {
        String userName= userRepository.findById(u).get().getUsername();
        List<Game> games= findByUsername(userName);
        if(games.size()==0){
            return false;
        }
        for (Game game : games) {
            if(gProperties(game,userName).getIsGameWinner()){
                return true;
            }
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Boolean gana5partidas(Integer u) throws DataAccessException {
        String userName= userRepository.findById(u).get().getUsername();
        List<Game> games= findByUsername(userName);
        if(games.size()<5){
            return false;
        }
        Integer numVic =0 ;
        for(Game g:games){
            if(gProperties(g,userName).getIsGameWinner()){
                numVic++;
            }
        }
        if(numVic<5){
            return false;
        }
        return true;
    }

    @Transactional(readOnly = true)
    public Boolean juegaMasde100min(Integer u) throws DataAccessException {
        Double tiempo=timePlayedByUserName(u);
        if(tiempo<100){
            return false;
        }
        return true;
    }

}