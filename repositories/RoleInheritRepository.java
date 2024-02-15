package com.tms.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tms.models.RoleInherit;

public interface RoleInheritRepository extends JpaRepository<RoleInherit, Long> {

//	List<RoleInherit> findByRoleName(String name);
	
	 Set<RoleInherit>  findByRoleName(String name);

}
