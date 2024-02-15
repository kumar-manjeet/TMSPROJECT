package com.tms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tms.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

	Permission findByModuleAndPermissionName(String module, String permission);

}
