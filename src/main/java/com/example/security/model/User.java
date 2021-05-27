package com.example.security.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/*import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;*/

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true,nullable = false)
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	//private boolean isActive;
	@Column(unique = true,nullable = false)
	private String tel;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="user_role", joinColumns = {@JoinColumn(name="user_id",referencedColumnName = "id")} , 
	inverseJoinColumns = {@JoinColumn(name="role_id",referencedColumnName = "id")})
	private List<Role> roles;
	//private List<RxCapteur> rxCapteurs;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Long getId() {
		return id;
	}
	/*public List<RxCapteur> getRxCapteurs() {
		return rxCapteurs;
	}
	public void setRxCapteurs(List<RxCapteur> rxCapteurs) {
		this.rxCapteurs = rxCapteurs;
	}
	*/
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public User(Long id, String email, String firstName, String lastName, String password, String tel,
			List<Role> roles) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.tel = tel;
		this.roles = roles;
	}
	public User(String email, String firstName, String lastName, String password, String tel,
			List<Role> roles) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.tel = tel;
		this.roles = roles;
	}
	public User() {
		super();
	}
	/*public void setRxCapteur(List<RxCapteur> rxCapteurs) {
		this.rxCapteurs= rxCapteurs;
	}*/
	
	
		
}
