package org.springframework.samples.petclinic.symbol;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SymbolService {

    private SymbolRepository symbolRepository;

    @Autowired
    public SymbolService(SymbolRepository symbolRepository) {
        this.symbolRepository = symbolRepository;
    }

    @Transactional
    public Symbol saveSymbol(Symbol symbol) throws DataAccessException {
        symbolRepository.save(symbol);
        return symbol;
    }

    @Transactional(readOnly = true)
    public List<Symbol> findAllSymbols() {
        return symbolRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Symbol findSymbolById(int id) throws DataAccessException {
        Optional<Symbol> symbol = symbolRepository.findById(id);
        return symbol.isEmpty()?null:symbol.get();
    }

}
