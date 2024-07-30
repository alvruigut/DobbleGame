package org.springframework.samples.petclinic.card;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Integer> {
    
    List<Card> findAll();
}
