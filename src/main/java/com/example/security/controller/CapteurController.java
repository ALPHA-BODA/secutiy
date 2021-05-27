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

import com.example.security.model.Capteur;
import com.example.security.repository.CapteurRepository;

import exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/capteurs")
@CrossOrigin("*")
public class CapteurController {
	
	
	@Autowired
	private CapteurRepository capteurRepository;
	

    @GetMapping("/getAllCapteurs")
    public List<Capteur> findAllCapteurs() {
        return capteurRepository.findAll();
    }
    
    @GetMapping("/findCapteur/{id}")
    public Optional<Capteur> findCapteur(@PathVariable Long id) {
        return capteurRepository.findById(id);
    }
    
    @DeleteMapping("/cancel/{id}")
    public List<Capteur> cancelCapteur(@PathVariable int id) {
        capteurRepository.deleteById((long) id);
        return capteurRepository.findAll();
    }
    
    @PostMapping("/capteurs")
	public Capteur createCapteur(@Validated @RequestBody Capteur capteur) {
		return capteurRepository.save(capteur);
	}
    
    @DeleteMapping("/capteurs/delete")
	  public ResponseEntity<String> deleteAllCapteurs() {
	    System.out.println("Delete All Sensors...");
	    capteurRepository.deleteAll();
	    return new ResponseEntity<>("All Sensors have been deleted!", HttpStatus.OK);
	  }
    
	@PutMapping("/capteurs/{id}")
	public ResponseEntity<Capteur> updateCapteur(@PathVariable(value = "id") Long id,
			@Validated @RequestBody Capteur capteur) throws ResourceNotFoundException {
		Capteur capt = capteurRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Sensor not found for this id :: " + id));
		capt.setName(capteur.getName());
		
		final Capteur updateCapteur = capteurRepository.save(capt);
		return ResponseEntity.ok(updateCapteur);
		}
	
	@DeleteMapping("/capteurs/{id}")
	public Map<String, Boolean> deleteCapteur(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		Capteur capt = capteurRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sensor not found for this id :: " + id));
		capteurRepository.delete(capt);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	

}
