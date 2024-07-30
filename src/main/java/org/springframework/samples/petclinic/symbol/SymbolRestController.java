package org.springframework.samples.petclinic.symbol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/symbols")
public class SymbolRestController {

    private final SymbolService symbolService;

    @Autowired
    public SymbolRestController(SymbolService symbolService) {
        this.symbolService = symbolService;
    }

    @GetMapping()
    public List<Symbol> getAllSymbols() {
        return symbolService.findAllSymbols();
    }

    @GetMapping(value = "{symbolId}")
    public @ResponseBody Symbol getSymbolById(@PathVariable("symbolId") int id) {
        Symbol s = symbolService.findSymbolById(id);
        if(s == null) {
            throw new ResourceNotFoundException("No existe el símbolo con id: " + id);
        }
        return s;
    }
    
}
