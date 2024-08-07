package org.springframework.samples.petclinic.auth;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.auth.payload.request.SignupRequest;
import org.springframework.samples.petclinic.clinic.ClinicService;
import org.springframework.samples.petclinic.clinicowner.ClinicOwner;
import org.springframework.samples.petclinic.clinicowner.ClinicOwnerService;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.Authorities;
import org.springframework.samples.petclinic.user.AuthoritiesService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private final PasswordEncoder encoder;
	private final AuthoritiesService authoritiesService;
	private final UserService userService;
	private final OwnerService ownerService;
	private final VetService vetService;
	private final ClinicOwnerService clinicOwnerService;
	private final ClinicService clinicService;
	private final PlayerService playerService;

	@Autowired
	public AuthService(PasswordEncoder encoder, AuthoritiesService authoritiesService, UserService userService,
			OwnerService ownerService, VetService vetService, ClinicOwnerService clinicOwnerService, ClinicService clinicService,
			PlayerService playerService) {
		this.encoder = encoder;
		this.authoritiesService = authoritiesService;
		this.userService = userService;
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.clinicOwnerService = clinicOwnerService;
		this.clinicService = clinicService;
		this.playerService = playerService;
	}

	@Transactional
	public void createUser(@Valid SignupRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(encoder.encode(request.getPassword()));
		String strRoles = request.getAuthority();
		Authorities role;

		switch (strRoles.toLowerCase()) {
		case "admin":
			role = authoritiesService.findByAuthority("ADMIN");
			user.setAuthority(role);
			userService.saveUser(user);
			break;
		case "clinic owner":
			role = authoritiesService.findByAuthority("CLINIC_OWNER");
			user.setAuthority(role);
			userService.saveUser(user);
			ClinicOwner clinicOwner = new ClinicOwner();
			clinicOwner.setFirstName(request.getFirstName());
			clinicOwner.setLastName(request.getLastName());
			clinicOwner.setUser(user);
			clinicOwnerService.saveClinicOwner(clinicOwner);
			break;
		case "owner":
			role = authoritiesService.findByAuthority("OWNER");
			user.setAuthority(role);
			userService.saveUser(user);
			Owner owner = new Owner();
			owner.setFirstName(request.getFirstName());
			owner.setLastName(request.getLastName());
			owner.setAddress(request.getAddress());
			owner.setCity(request.getCity());
			owner.setTelephone(request.getTelephone());
			owner.setClinic(clinicService.findClinicById(request.getClinic().getId()));
			owner.setUser(user);
			ownerService.saveOwner(owner);
			break;
		case "player":
			role = authoritiesService.findByAuthority("PLAYER");
			user.setAuthority(role);
			userService.saveUser(user);
			Player player = new Player();
			player.setFirstName(request.getFirstName());
			player.setLastName(request.getLastName());
			player.setEmail(request.getEmail());
			player.setTelephone(request.getTelephone());
			player.setUser(user);
			playerService.savePlayer(player);
		}

	}

}
