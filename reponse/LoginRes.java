package com.tms.models.reponse;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class LoginRes {
	
	private String email;
	private String token;
	private Collection<? extends GrantedAuthority> role;
	
	
	public LoginRes(String email,String token,Collection<? extends GrantedAuthority> authorities) {
		this.email = email;
		this.token = token;
		this.role = authorities;
	}

	
	public LoginRes(String email,String token) {
		this.email = email;
		this.token = token;
		
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public Collection<? extends GrantedAuthority> getRole() {
		return role;
	}


	public void setRole(Collection<? extends GrantedAuthority> role) {
		this.role = role;
	}
	
	

	
}

