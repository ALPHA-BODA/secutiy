package com.example.security.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DonneCapteur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
    private Long id;
	
	private Long nivEau;
	private Long nivBat;
	private Date dateEnvoi; 
	private Date dateDernRecep;
	public DonneCapteur() {
		super();
	}
	public DonneCapteur(Long id, Long nivEau, Long nivBat, Date dateEnvoi, Date dateDernRecep) {
		super();
		this.id=id;
		this.nivEau = nivEau;
		this.nivBat=nivBat;
		this.dateEnvoi = dateEnvoi;
		this.dateDernRecep = dateDernRecep;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNivEau() {
		return nivEau;
	}
	public void setNivEau(Long nivEau) {
		this.nivEau = nivEau;
	}
	public Date getDateEnvoi() {
		return dateEnvoi;
	}
	
	public void setNivBat(Long nivBat) {
		this.nivBat= nivBat;
	}
	
	public Long getNivBat() {
		return nivBat;
	}
	public void setDateEnvoi(Date dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}
	public Date getDateDernRecep() {
		return dateDernRecep;
	}
	public void setDateDernRecep(Date dateDernRecep) {
		this.dateDernRecep = dateDernRecep;
	}

}
