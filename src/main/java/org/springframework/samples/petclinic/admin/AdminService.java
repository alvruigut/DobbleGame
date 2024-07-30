package org.springframework.samples.petclinic.admin;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    
    private AdminRepository adminRepository;

    @Autowired
	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

    @Transactional(readOnly = true)
	public Iterable<Admin> findAll() throws DataAccessException {
		return adminRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Admin findAdminById(int id) throws DataAccessException {
		return this.adminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin", "ID", id));
	}

	@Transactional
	public Admin saveAdmin(Admin admin) throws DataAccessException {
		adminRepository.save(admin);
		return admin;
	}

	@Transactional
	public Admin updateAdmin(Admin admin, int id) throws DataAccessException {
		Admin toUpdate = findAdminById(id);
		BeanUtils.copyProperties(admin, toUpdate, "id", "user");
		return saveAdmin(toUpdate);
	}

	@Transactional
	public void deleteAdmin(int id) throws DataAccessException {
		Admin toDelete = findAdminById(id);
		adminRepository.delete(toDelete);
	}
}
