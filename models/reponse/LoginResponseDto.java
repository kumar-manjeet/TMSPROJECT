package com.tms.models.reponse;

import java.util.List;

public class LoginResponseDto {

	private String token;
	private List<String> roles;
	private Object rolesData;
	private String userName;
	private String mId;
	
	public String getToken() {
		return token;
	}
	public List<String> getRoles() {
		return roles;
	}
	public String getUserName() {
		return userName;
	}
	public String getmId() {
		return mId;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	
	
	public Object getRolesData() {
		return rolesData;
	}
	public void setRolesData(Object rolesData) {
		this.rolesData = rolesData;
	}
	@Override
	public String toString() {
		return "LoginResponseDto [token=" + token + ", roles=" + roles + ", userName=" + userName + ", mId=" + mId
				+ "]";
	}
	
	
}
