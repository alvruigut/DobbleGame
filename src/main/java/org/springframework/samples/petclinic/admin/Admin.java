package org.springframework.samples.petclinic.admin;

//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "players")

public class Admin extends Person {

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

    
}
