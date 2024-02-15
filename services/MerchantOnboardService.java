package com.tms.services;

import java.util.List;
import java.util.Optional;

import com.tms.models.Documents;
import com.tms.models.MerchantOnboard;
import com.tms.models.reponse.ResponseDto;
import com.tms.models.request.MerchantOnboardDto;

public interface MerchantOnboardService {

	public List<MerchantOnboard> getOnboardedMerchant();

	public Long saveDocuments(Documents document);

	public Long merchantOnboard(MerchantOnboardDto merchantOnboardDto, String password);

	public String generatePassword(int length, boolean includeUppercase, boolean includeDigits, boolean includeSpecialChars);

	public ResponseDto verifyOnboardedMerchant(String email, String generatedPassword, String newPassword);

	public boolean existsByMerchantName(String merchantName);

	public boolean existsByEmail(String email);

	public boolean existsByMobileNumber(String mobileNumber);

	public Optional<MerchantOnboard> getOnboardedMerchantById(Long id);
}
