package com.example.security;


//import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.security.model.Capteur;
import com.example.security.model.DonneCapteur;
import com.example.security.model.Role;
import com.example.security.model.RxCapteur;
//import com.example.security.model.RxCapteur;
import com.example.security.model.User;
import com.example.security.repository.CapteurRepository;
import com.example.security.repository.DonneCapteurRepository;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.RxCapteurRepository;
//import com.example.security.repository.RxCapteurRepository;
import com.example.security.repository.UserRepository;

//import io.jsonwebtoken.lang.Arrays;
//import io.jsonwebtoken.lang.Collections;

@SpringBootApplication
public class SecurityApplication implements CommandLineRunner{

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DonneCapteurRepository donneeCapteurRepository;
	
	@Autowired
	private CapteurRepository capteurRepository;
	
	@Autowired
	private RxCapteurRepository rxCapteurRepository;
	
	@Autowired
	private RoleRepository roleRepository;
		
	@Autowired
	private PasswordEncoder encoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Role role = roleRepository.save(new Role("admin"));
		//RxCapteur rxCapteur= rxCapteurRepository.save(new RxCapteur(null, "rx1"));
		User user = new User();
		user.setEmail("user@gmail.fr");
		user.setPassword(encoder.encode("password"));
		user.setRoles(java.util.Collections.singletonList(role));
		user.setTel("+216 54 175 896");
		//user.setRxCapteur(java.util.Collections.singletonList(rxCapteur));
		userRepository.save(user);
		
		
		Role r =roleRepository.save(new Role("user"));
		User u = new User();
		u.setEmail("ali@gmail.com");
		u.setPassword(encoder.encode("motdepasse"));
		u.setRoles(java.util.Collections.singletonList(r));
		u.setTel("+216 54 175 457");
		userRepository.save(u);	
		
		Capteur capteur= new Capteur();
		capteur.setName("capteur1");
		capteurRepository.save(capteur);	
		
		RxCapteur rxcapteur= new RxCapteur();
		rxcapteur.setName("rxcapteur1");
		rxCapteurRepository.save(rxcapteur);
		
		
		DonneCapteur donnee= new DonneCapteur();
		donnee.setNivBat((long) 20);
		donnee.setNivEau((long)30);
		donnee.setDateEnvoi(null);
		donnee.setDateDernRecep(null);
		donneeCapteurRepository.save(donnee);
		
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}
