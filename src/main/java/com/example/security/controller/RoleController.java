package com.example.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.model.Role;
import com.example.security.repository.RoleRepository;

import exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin("*")
public class RoleController {

	
	@Autowired
	private RoleRepository roleRepository;
	

    @GetMapping("/getAllRoles")
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
    
    @GetMapping("/findRole/{id}")
    public Optional<Role> findRole(@PathVariable Long id) {
        return roleRepository.findById(id);
    }
    
    @DeleteMapping("/cancel/{id}")
    public List<Role> cancelRole(@PathVariable int id) {
    	roleRepository.deleteById((long) id);
        return roleRepository.findAll();
    }
    
    @PostMapping("/roles")
	public Role createRole(@Validated @RequestBody Role role) {
		return roleRepository.save(role);
	}
    
    @DeleteMapping("/roles/delete")
	  public ResponseEntity<String> deleteAllRoles() {
	    System.out.println("Delete All Roles...");
	    roleRepository.deleteAll();
	    return new ResponseEntity<>("All Roles have been deleted!", HttpStatus.OK);
	  }
    
	@PutMapping("/roles/{id}")
	public ResponseEntity<Role> updateRole(@PathVariable(value = "id") Long id,
			@Validated @RequestBody Role role) throws ResourceNotFoundException {
		Role rl = roleRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + id));
		rl.setName(role.getName());
		
		final Role updateRole = roleRepository.save(rl);
		return ResponseEntity.ok(updateRole);
		}
	
	@DeleteMapping("/roles/{id}")
	public Map<String, Boolean> deleteRole(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + id));
		roleRepository.delete(role);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	

}
