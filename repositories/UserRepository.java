package com.tms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.models.Employee;
import com.tms.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

//	public User findUserByEmail(String email);

	public User findByUsernameOrEmail(String username, String username2);

	public boolean existsByEmail(String email);

	public boolean existsByUsername(String userName);

	public boolean existsByMobile(String mobile);

	public User findByUsername(String username);

	public User save(Employee em);
	
	public User findByEmail(String email);
	
}