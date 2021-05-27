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

import com.example.security.model.RxCapteur;
import com.example.security.repository.RxCapteurRepository;

import exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/rxCapteurs")
@CrossOrigin("*")
public class RxCapteurController {
	
	@Autowired
	private RxCapteurRepository rxcapteurRepository;
	

    @GetMapping("/getAllCapteurs")
    public List<RxCapteur> findAllCapteurs() {
        return rxcapteurRepository.findAll();
    }
    
    @GetMapping("/findCapteur/{id}")
    public Optional<RxCapteur> findCapteur(@PathVariable Long id) {
        return rxcapteurRepository.findById(id);
    }
    
    @DeleteMapping("/cancel/{id}")
    public List<RxCapteur> cancelCapteur(@PathVariable int id) {
    	rxcapteurRepository.deleteById((long) id);
        return rxcapteurRepository.findAll();
    }
    
    @PostMapping("/rxcapteurs")
	public RxCapteur createRxCapteur(@Validated @RequestBody RxCapteur rxcapteur) {
		return rxcapteurRepository.save(rxcapteur);
	}
    
    @DeleteMapping("/rxcapteurs/delete")
	  public ResponseEntity<String> deleteAllRxCapteurs() {
	    System.out.println("Delete All Sensors networks...");
	    rxcapteurRepository.deleteAll();
	    return new ResponseEntity<>("All Sensors networks have been deleted!", HttpStatus.OK);
	  }
    
	@PutMapping("/rxcapteurs/{id}")
	public ResponseEntity<RxCapteur> updateRxCapteur(@PathVariable(value = "id") Long id,
			@Validated @RequestBody RxCapteur rxcapteur) throws ResourceNotFoundException {
		RxCapteur rxcapt = rxcapteurRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Sensor networks not found for this id :: " + id));
		rxcapt.setName(rxcapteur.getName());
		
		final RxCapteur updateRxCapteur = rxcapteurRepository.save(rxcapt);
		return ResponseEntity.ok(updateRxCapteur);
		}
	
	@DeleteMapping("/rxcapteurs/{id}")
	public Map<String, Boolean> deleteRxCapteur(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		RxCapteur rxcapt = rxcapteurRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sensor network not found for this id :: " + id));
		rxcapteurRepository.delete(rxcapt);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	


}
