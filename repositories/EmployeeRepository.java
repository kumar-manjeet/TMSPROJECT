package com.tms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
	
	
	Optional<Employee> findById(Long id);
	
	Boolean existsByUsername(String name);
	
	Boolean existsByEmail(String email);
	
	Boolean existsByMobile(String mobile);
	
	boolean existsById(Long id);

}
