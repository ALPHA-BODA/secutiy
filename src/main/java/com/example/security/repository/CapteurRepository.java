package com.example.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.model.Capteur;

@Repository
public interface CapteurRepository extends JpaRepository<Capteur, Long>{
	Optional<Capteur> findByName(String name);
	

}
