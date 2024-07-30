package org.springframework.samples.petclinic.admin;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.auth.payload.response.MessageResponse;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.util.RestPreconditions;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "bearerAuth")
public class AdminRestController {

	private final AdminService adminService;
	private final UserService userService;

    @Autowired
    public AdminRestController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

     @GetMapping
	public ResponseEntity<List<Admin>> findAll() {
		return new ResponseEntity<>((List<Admin>) adminService.findAll(), HttpStatus.OK);
	}

    @GetMapping("/{adminId}")
	public ResponseEntity<Admin> findById(@PathVariable("adminId") int id) {
		return new ResponseEntity<>(adminService.findAdminById(id), HttpStatus.OK);
	}

    @PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Admin> create(@RequestBody @Valid Player admin) throws URISyntaxException {
		Admin newAdmin = new Admin();
		BeanUtils.copyProperties(admin, newAdmin, "id");
		User user = userService.findCurrentUser();
		newAdmin.setUser(user);
		Admin savedAdmin = this.adminService.saveAdmin(newAdmin);

		return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
	}

    @PutMapping("/{adminId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Admin> update(@PathVariable("adminId") int adminId, @RequestBody @Valid Admin Admin) {
		RestPreconditions.checkNotNull(adminService.findAdminById(adminId), "Admin", "ID", adminId);
		return new ResponseEntity<>(this.adminService.updateAdmin(Admin, adminId), HttpStatus.OK);
	}

	@DeleteMapping("/{adminId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<MessageResponse> delete(@PathVariable("adminId") int id) {
		RestPreconditions.checkNotNull(adminService.findAdminById(id), "Admin", "ID", id);
		adminService.deleteAdmin(id);
		return new ResponseEntity<>(new MessageResponse("Admin deleted!"), HttpStatus.OK);
	}
}
