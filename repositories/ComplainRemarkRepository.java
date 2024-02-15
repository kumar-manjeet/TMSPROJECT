package com.tms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tms.models.ComplainRemark;


public interface ComplainRemarkRepository extends JpaRepository<ComplainRemark, Long>{

	Optional<ComplainRemark> findByComplainId(Long complaintId);

}
