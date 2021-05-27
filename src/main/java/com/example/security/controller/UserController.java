package com.example.security.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.example.security.config.Message;
import com.example.security.dto.LoginRequest;
import com.example.security.dto.LoginResponse;
import com.example.security.dto.RegisterRequest;
import com.example.security.model.Role;
import com.example.security.model.User;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;
import com.example.security.service.UserDetailsImpl;
import com.example.security.util.JwtUtil;

import exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {	
	
	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	private RoleRepository roleRepository;
	
	
	@Autowired
	private PasswordEncoder encoder;
	

	@Autowired
	private UserDetailsImpl userDetailsImpl;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {

		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(e.getMessage());
		}
		final UserDetails userDetails = userDetailsImpl.loadUserByUsername(loginRequest.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails);

		return new LoginResponse(jwt);

	}
	
	
	@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest data) {
		if(userRepository.existsByEmail(data.getEmail())) {
			return ResponseEntity.badRequest().body(new Message("Error: This Email is already in use!"));
		}
		Role role = roleRepository.findRoleByName("user");
		User user = new User(data.getEmail(), data.getFirstname(), data.getLastname(),  encoder.encode(data.getPassword()), data.getTel(), Arrays.asList(role));
		userRepository.save(user);
		return ResponseEntity.ok(new Message ("User registered successfully!"));
    } 


    @GetMapping("/getAllUsers")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/findUser/{email}")
    public Optional<User> findUser(@PathVariable String email) {
        return userRepository.findByEmail(email);
    }
    
    
    @DeleteMapping("/cancel/{id}")
    public List<User> cancelRegistration(@PathVariable int id) {
        userRepository.deleteById((long) id);
        return userRepository.findAll();
    }
    
	@PostMapping("/users")
	public User createUser(@Validated @RequestBody User user) {
		return userRepository.save(user);
	}
	
	@DeleteMapping("/users/delete")
	  public ResponseEntity<String> deleteAllUsers() {
	    System.out.println("Delete All Users...");
	 
	    userRepository.deleteAll();
	 
	    return new ResponseEntity<>("All Users have been deleted!", HttpStatus.OK);
	  }


	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id,
			@Validated @RequestBody User userDetails) throws ResourceNotFoundException {
		User user = userRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
		user.setEmail(userDetails.getEmail());
		user.setLastName(userDetails.getLastName());
		user.setFirstName(userDetails.getFirstName());
		user.setTel(userDetails.getTel());
		user.setRoles(userDetails.getRoles());
		final User updateUser = userRepository.save(user);
		return ResponseEntity.ok(updateUser);
	}

	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	

}
