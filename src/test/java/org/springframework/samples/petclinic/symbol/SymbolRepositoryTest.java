package org.springframework.samples.petclinic.symbol;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@DataJpaTest
@SpringJUnitConfig
public class SymbolRepositoryTest {

    @Autowired
    private SymbolRepository symbolRepository;

    @MockBean
    private SymbolRepository mockSymbolRepository;
    
    @Test
    void testFindAll() {
        Symbol symbol1 = new Symbol();
        Symbol symbol2 = new Symbol();
        List<Symbol> symbols = Arrays.asList(symbol1, symbol2);

        when(mockSymbolRepository.findAll()).thenReturn(symbols);

        List<Symbol> result = symbolRepository.findAll();

        assertEquals(symbols.size(), result.size());
    }
}
