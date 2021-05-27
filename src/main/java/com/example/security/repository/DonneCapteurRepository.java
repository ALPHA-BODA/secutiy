package com.example.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.security.model.DonneCapteur;
@Repository
public interface DonneCapteurRepository extends JpaRepository<DonneCapteur, Long> {
	
	Optional<DonneCapteur> findById(String id);
}
