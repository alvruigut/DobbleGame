package org.springframework.samples.petclinic.card;

import java.util.List;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.symbol.Symbol;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
@Entity
public class Card extends BaseEntity {

    public static final int MAX_CARDS = 8;
    
    @ManyToMany
    @Size(max = MAX_CARDS)
    public List<Symbol> symbols;

    public Card(int cardId) {
        this.id = cardId;
    }

}
