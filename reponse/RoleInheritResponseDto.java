package com.tms.models.reponse;

public class RoleInheritResponseDto {

	private String roleName;
	
	private Boolean status;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RoleInheritResponseDto [roleName=" + roleName + ", status=" + status + "]";
	}

}
