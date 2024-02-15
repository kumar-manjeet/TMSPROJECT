package com.tms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tms.models.Documents;

public interface DocumentsRepository extends JpaRepository<Documents, Long>{

}
