package com.tms.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role_inherit")
public class RoleInherit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="role_name")
	private String roleName;
	
	@Column(name="role_inherited")
	private String roleInherited;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleInherited() {
		return roleInherited;
	}

	public void setRoleInherited(String roleInherited) {
		this.roleInherited = roleInherited;
	}

	@Override
	public String toString() {
		return "RoleInherit [id=" + id + ", roleName=" + roleName + ", roleInherited=" + roleInherited + "]";
	}
	
	
}
