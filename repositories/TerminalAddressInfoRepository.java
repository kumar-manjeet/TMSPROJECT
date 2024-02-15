package com.tms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.models.TerminalAddressInfo;

@Repository
public interface TerminalAddressInfoRepository extends JpaRepository<TerminalAddressInfo, Long> {
	
	public TerminalAddressInfo findByTerminalId(Long id);
	
	public boolean existsByTerminalId(Long id);
	
	public boolean existsByIpAddress(String ip);

}
