package com.example.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.security.model.Role;
import com.example.security.model.User;
import com.example.security.repository.UserRepository;

@Service
public class UserDetailsImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByEmail(email);

		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			
			return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),getAuthorities(user.getRoles()));
		}
	 throw new UsernameNotFoundException("User with email: "+email+" do not exist.");
	}
	
	private Collection<GrantedAuthority> getAuthorities(Collection<Role> roles){
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(role->authorities.add(new SimpleGrantedAuthority(role.getName())));
		return authorities;
		
	}

	
}
