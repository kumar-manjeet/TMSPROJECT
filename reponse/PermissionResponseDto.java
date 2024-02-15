package com.tms.models.reponse;

public class PermissionResponseDto {

	private Long permissionId;
	private String permissionName;
	private Boolean status;
	
	public Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "PermissionResponseDto [permissionId=" + permissionId + ", permissionName=" + permissionName
				+ ", status=" + status + "]";
	}

}
