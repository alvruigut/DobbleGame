package org.springframework.samples.petclinic.symbol;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class SymbolServiceTest {

    @Mock
    private SymbolRepository symbolRepository;

    @InjectMocks
    private SymbolService symbolService;

    @Test
    @Transactional
    public void testSaveSymbol() {
        Symbol symbolToSave = new Symbol();
        symbolToSave.setId(1);

        when(symbolRepository.save(symbolToSave)).thenReturn(symbolToSave);

        Symbol result = symbolService.saveSymbol(symbolToSave);

        assertEquals(symbolToSave.getId(), result.getId());

        verify(symbolRepository).save(symbolToSave);
    }

    @Test
    @Transactional(readOnly = true)
    public void testFindAllSymbols() {
        Symbol symbol1 = new Symbol();
        symbol1.setId(1);

        Symbol symbol2 = new Symbol();
        symbol2.setId(2);

        Symbol symbol3 = new Symbol();
        symbol3.setId(3);

        Symbol symbol4 = new Symbol();
        symbol4.setId(4);

        List<Symbol> symbols = new ArrayList<>();
        symbols.add(symbol1);
        symbols.add(symbol2);
        symbols.add(symbol3);
        symbols.add(symbol4);

        when(symbolRepository.findAll()).thenReturn(symbols);

        List<Symbol> result = symbolService.findAllSymbols();

        assertEquals(symbols.size(), result.size());
        assertEquals(symbols.get(0).getId(), result.get(0).getId());
        assertEquals(symbols.get(1).getId(), result.get(1).getId());
        assertEquals(symbols.get(2).getId(), result.get(2).getId());
        assertEquals(symbols.get(3).getId(), result.get(3).getId());

    }
    
    @Test
    @Transactional(readOnly = true)
    public void testFindSymbolById() {
        int symbolId = 1;
        Symbol symbol = new Symbol();
        symbol.setId(symbolId);

        when(symbolRepository.findById(symbolId)).thenReturn(Optional.of(symbol));

        Symbol result = symbolService.findSymbolById(symbolId);

        assertEquals(symbol.getId(), result.getId());

        int nonExistingSymbolId = 100;
        when(symbolRepository.findById(nonExistingSymbolId)).thenReturn(Optional.empty());

        Symbol nonExistintSymbol = symbolService.findSymbolById(nonExistingSymbolId);
        assertNull(nonExistintSymbol);
    }
}
