package org.springframework.samples.petclinic.symbol;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class SymbolControllerTest {

    @Mock
    private SymbolService symbolService;

    @InjectMocks
    private SymbolRestController symbolRestController;

    @Test
    void testGetAllSymbols() {
        Symbol symbol1 = new Symbol();
        symbol1.setId(1);

        Symbol symbol2 = new Symbol();
        symbol2.setId(2);

        List<Symbol> symbols = new ArrayList<>();
        symbols.add(symbol1);
        symbols.add(symbol2);

        when(symbolService.findAllSymbols()).thenReturn(symbols);

        List<Symbol> result = symbolRestController.getAllSymbols();
        assertEquals(symbols, result);
    }

    @Test
    void testGetSymbolByIdWithCorrectId() {
        Symbol symbol = new Symbol();
        symbol.setId(1);

        when(symbolService.findSymbolById(1)).thenReturn(symbol);

        Symbol result = symbolRestController.getSymbolById(1);
        
        assertEquals(symbol, result);
    }

    @Test
    void testGetSymbolByIdWithIncorrectId() {
        when(symbolService.findSymbolById(2)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> symbolRestController.getSymbolById(2));
    }
    
}
