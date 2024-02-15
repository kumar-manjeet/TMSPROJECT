package com.tms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tms.models.Complain;

public interface ComplainRepository extends JpaRepository<Complain, Long> {

	List<Complain> findByStatus(String status);

}
