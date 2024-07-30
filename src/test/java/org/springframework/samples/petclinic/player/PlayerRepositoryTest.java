package org.springframework.samples.petclinic.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.gameProperties.GameProperties;
import org.springframework.samples.petclinic.gameProperties.GamePropertiesRepository;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;
import org.springframework.samples.petclinic.roundProperties.RoundPropertiesRepository;
import org.springframework.samples.petclinic.user.User;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@DataJpaTest
@SpringJUnitConfig
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RoundPropertiesRepository roundPropertiesRepository;

    @MockBean
    private RoundPropertiesRepository mockRoundPropertiesRepository;

    @MockBean
    private PlayerRepository mockPlayerRepository;

    @MockBean
    private GamePropertiesRepository gamePropertiesRepository;

    @Test
    void testFindByLastName() {
        String lastName = "Smith";
        Player player1 = new Player();
        player1.setFirstName("John");
        Player player2 = new Player();
        player2.setFirstName("Jane");

        when(mockPlayerRepository.findByLastName(lastName)).thenReturn(Arrays.asList(player1, player2));

        Collection<Player> result = playerRepository.findByLastName(lastName);

        assertEquals(2, result.size());
        List<Player> resultList = Arrays.asList(result.toArray(new Player[0]));
        assertEquals("John", resultList.get(0).getFirstName());
        assertEquals("Jane", resultList.get(1).getFirstName());
    }

    @Test
    void testFindByFirstName() {
        String firstName = "John";
        Player player1 = new Player();
        player1.setLastName("Smith");
        Player player2 = new Player();
        player2.setLastName("Doe");

        when(mockPlayerRepository.findByFirstName(firstName)).thenReturn(Arrays.asList(player1, player2));

        Collection<Player> result = playerRepository.findByFirstName(firstName);

        assertEquals(2, result.size());
        List<Player> resultList = Arrays.asList(result.toArray(new Player[0]));
        assertEquals("Smith", resultList.get(0).getLastName());
        assertEquals("Doe", resultList.get(1).getLastName());
    }
    
    @Test
    void testFindByUsername() {
        String username = "john_doe";
        User user = new User();
        user.setUsername(username);
        Player player = new Player();
        player.setFirstName("John");
        player.setLastName("Doe");

        when(mockPlayerRepository.findByUsername(username)).thenReturn(player);

        Player result = playerRepository.findByUsername(username);

        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

}
