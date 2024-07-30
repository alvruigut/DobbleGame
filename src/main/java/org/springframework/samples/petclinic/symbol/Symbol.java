package org.springframework.samples.petclinic.symbol;

import org.springframework.samples.petclinic.model.NamedEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "symbol")
@Getter
@Setter
public class Symbol extends NamedEntity{
    
    
}