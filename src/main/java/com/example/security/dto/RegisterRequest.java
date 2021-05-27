package com.example.security.dto;

//import com.example.security.model.Role;

public class RegisterRequest {
	
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String tel;
	//public Role role;
	public RegisterRequest() {
		super();
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public void setTel(String telephone) {
		this.tel = telephone;
	}
	public RegisterRequest(String firstname, String lastname, String email, String password, String telephone) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.tel = telephone;
	}

}
