package com.tms.models.request;

import java.util.List;

public class SaveRolePermissonRequestDto {

	private Long roleId;
	private List<SaveRolePermissonDto> saveRolePermissonDto;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public List<SaveRolePermissonDto> getSaveRolePermissonDto() {
		return saveRolePermissonDto;
	}
	public void setSaveRolePermissonDto(List<SaveRolePermissonDto> saveRolePermissonDto) {
		this.saveRolePermissonDto = saveRolePermissonDto;
	}
	@Override
	public String toString() {
		return "SaveRolePermissonRequestDto [roleId=" + roleId + ", saveRolePermissonDto=" + saveRolePermissonDto + "]";
	}
	
	
}
