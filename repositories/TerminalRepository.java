package com.tms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.models.Terminal;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Long> {

	boolean existsByIccId(Long id);
	
	boolean existsByImsi(Long id);
	
	boolean existsByCurrentRsi(Long id);
	
	boolean existsByTerminalId(Long id);
	
	boolean existsByRefId(Long id);
	
	Terminal findByRefId(Long id);

	Terminal findByTerminalId(Long id);

}
