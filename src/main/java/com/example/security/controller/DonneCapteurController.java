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

import com.example.security.model.DonneCapteur;
import com.example.security.repository.DonneCapteurRepository;

import exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/dataCapteurs")
@CrossOrigin("*")
public class DonneCapteurController {
	
	@Autowired
	private DonneCapteurRepository dcapteurRepository;
	

    @GetMapping("/getAllDataCapteurs")
    public List<DonneCapteur> findAllDonneCapteurs() {
        return dcapteurRepository.findAll();
    }
    
    @GetMapping("/findDataCapteur/{id}")
    public Optional<DonneCapteur> findDonneCapteur(@PathVariable Long id) {
        return dcapteurRepository.findById(id);
    }
    
    @DeleteMapping("/cancel/{id}")
    public List<DonneCapteur> cancelDonneCapteur(@PathVariable int id) {
    	dcapteurRepository.deleteById((long) id);
        return dcapteurRepository.findAll();
    }
    
    @PostMapping("/datacapteurs")
	public DonneCapteur createDonneCapteur(@Validated @RequestBody DonneCapteur datacapteur) {
		return dcapteurRepository.save(datacapteur);
	}
    
    @DeleteMapping("/datacapteurs/delete")
	  public ResponseEntity<String> deleteAllDonneCapteurs() {
	    System.out.println("Delete All Sensors data...");
	    dcapteurRepository.deleteAll();
	    return new ResponseEntity<>("All Sensors data have been deleted!", HttpStatus.OK);
	  }
    
	@PutMapping("/datacapteurs/{id}")
	public ResponseEntity<DonneCapteur> updateDonneCapteur(@PathVariable(value = "id") Long id,
			@Validated @RequestBody DonneCapteur dcapteur) throws ResourceNotFoundException {
		DonneCapteur dcapt = dcapteurRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Sensor data not found for this id :: " + id));
		dcapt.setId(dcapteur.getId());
		
		final DonneCapteur updatedCapteur = dcapteurRepository.save(dcapt);
		return ResponseEntity.ok(updatedCapteur);
		}
	
	@DeleteMapping("/datacapteurs/{id}")
	public Map<String, Boolean> deleteDonneCapteur(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		DonneCapteur dcapt = dcapteurRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sensor data  not found for this id :: " + id));
		dcapteurRepository.delete(dcapt);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	

	

}
