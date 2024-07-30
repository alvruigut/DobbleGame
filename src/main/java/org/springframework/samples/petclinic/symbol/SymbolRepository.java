package org.springframework.samples.petclinic.symbol;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolRepository extends CrudRepository<Symbol, Integer>{

    List<Symbol> findAll();

}
