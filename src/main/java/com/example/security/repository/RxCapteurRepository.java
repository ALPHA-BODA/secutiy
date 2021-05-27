package com.example.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.model.RxCapteur;
import com.example.security.model.User;

public interface RxCapteurRepository extends JpaRepository<RxCapteur, Long>{
	Optional<User> findByName(String name);


}
