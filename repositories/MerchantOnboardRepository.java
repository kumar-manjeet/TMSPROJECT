package com.tms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tms.models.MerchantOnboard;

public interface MerchantOnboardRepository extends JpaRepository<MerchantOnboard, Long> {

	public MerchantOnboard findByEmail(String email);

	public boolean existsByEmail(String email);

	public boolean existsByMerchantName(String merchantName);

	public boolean existsByMobileNumber(String mobile);

}
