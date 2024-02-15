package com.tms.models.request;

import java.util.ArrayList;

import com.tms.models.reponse.PermissionResponseDto;

public class SaveRolePermissonDto {

	private String moduleName;
	
	private ArrayList<PermissionResponseDto> permissionResponseDto;

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public ArrayList<PermissionResponseDto> getPermissionResponseDto() {
		return permissionResponseDto;
	}

	public void setPermissionResponseDto(ArrayList<PermissionResponseDto> permissionResponseDto) {
		this.permissionResponseDto = permissionResponseDto;
	}

	@Override
	public String toString() {
		return "SaveRolePermissonRequestDto [moduleName=" + moduleName + ", permissionResponseDto="
				+ permissionResponseDto + "]";
	}

}
