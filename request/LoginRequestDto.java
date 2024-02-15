package com.tms.models.request;

public class LoginRequestDto {

	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginRequestDto [username=" + username + ", password=" + password + "]";
	}
	
}
