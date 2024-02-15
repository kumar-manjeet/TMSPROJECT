package com.tms.services;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.security.SecureRandom;
import java.util.Random;
import com.tms.models.Documents;
import com.tms.models.MerchantOnboard;
import com.tms.models.User;
import com.tms.models.reponse.ResponseDto;
import com.tms.models.request.MerchantOnboardDto;
import com.tms.repositories.DocumentsRepository;
import com.tms.repositories.MerchantOnboardRepository;
import com.tms.utils.StatusResponse;

@Service
public class MerchantOnboardServiceImpl implements MerchantOnboardService {

	@Value("${upload.path}")
	private String uploadDir;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MerchantOnboardRepository merchantOnboardRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private DocumentsRepository documentsRepository;

	@Override
	public List<MerchantOnboard> getOnboardedMerchant() {
		List<MerchantOnboard> allOnboardedMerchant = null;
		try {
			allOnboardedMerchant = merchantOnboardRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allOnboardedMerchant;
	}

	@Override
	public Long saveDocuments(Documents document) {
		Documents savedDocuments = documentsRepository.save(document);
		return savedDocuments.getId();
	}

	@Override
	public boolean existsByMerchantName(String merchantName) {
		return merchantOnboardRepository.existsByMerchantName(merchantName);
	}

	@Override
	public boolean existsByEmail(String email) {
		return merchantOnboardRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByMobileNumber(String mobileNumber) {
		return merchantOnboardRepository.existsByMobileNumber(mobileNumber);
	}

	@Override
	public Long merchantOnboard(MerchantOnboardDto merchantOnboardDto, String password) {
		Documents document = new Documents();
		MerchantOnboard merchantOnboard = new MerchantOnboard();
		String name = merchantOnboardDto.getMerchantName();
		String mobileNumber = merchantOnboardDto.getMobileNumber();
		String productType = merchantOnboardDto.getProductType();
		String email = merchantOnboardDto.getEmail();
		String adharNumber = merchantOnboardDto.getAdharNumber();
		String panNumber = merchantOnboardDto.getPanNumber();
		String bankAccountNumber = merchantOnboardDto.getBankAccountNumber();
		String address = merchantOnboardDto.getAddress();
		MultipartFile merchantImage = merchantOnboardDto.getMerchantImage();
		MultipartFile adhar = merchantOnboardDto.getAdharcard();
		MultipartFile pan = merchantOnboardDto.getPancard();

		merchantOnboard.setMerchantName(name);
		merchantOnboard.setMobileNumber(mobileNumber);
		merchantOnboard.setProductType(productType);
		merchantOnboard.setAdharNumber(adharNumber);
		merchantOnboard.setPanNumber(panNumber);
		merchantOnboard.setBankAccountNumber(bankAccountNumber);
		merchantOnboard.setAddress(address);
		merchantOnboard.setEmail(email);
		merchantOnboard.setProductType(productType);
		merchantOnboard.setPassword(passwordEncoder.encode(password));
		merchantOnboard.setDocuments(document);
		String merchantFileName = merchantImage.getOriginalFilename();
		Path merchantImagePath = Paths.get(uploadDir + "/" + merchantFileName);
		String merchantImageUrl = merchantImagePath.toString();
		merchantOnboard.setMerchantImage(merchantImageUrl);

		String adharFileName = adhar.getOriginalFilename();
		String panFileName = pan.getOriginalFilename();
		Path adharFilePath = Paths.get(uploadDir + "/" + adharFileName);
		Path panFilePath = Paths.get(uploadDir + "/" + panFileName);
		String adharUrl = adharFilePath.toString();
		String panUrl = panFilePath.toString();
		document.setAdhar_card(adharUrl);
		document.setPan_card(panUrl);
		MerchantOnboard savedMerchant = merchantOnboardRepository.save(merchantOnboard);
		Documents savedDocument = documentsRepository.save(document);
		return savedMerchant.getId();
	}
	
	@Override
	public Optional<MerchantOnboard> getOnboardedMerchantById(Long id) {
		Optional<MerchantOnboard> merchant = merchantOnboardRepository.findById(id);
		return merchant;
	}

	private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String DIGITS = "0123456789";
	private static final String SPECIAL_CHARS = "!@#$%&*";

	@Override
	public String generatePassword(int length, boolean includeUppercase, boolean includeDigits,
			boolean includeSpecialChars) {
		StringBuilder password = new StringBuilder();
		String chars = LOWERCASE_CHARS;
		if (includeUppercase) {
			chars += UPPERCASE_CHARS;
		}
		if (includeDigits) {
			chars += DIGITS;
		}
		if (includeSpecialChars) {
			chars += SPECIAL_CHARS;
		}
		Random random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(chars.length());
			password.append(chars.charAt(randomIndex));
		}
		return password.toString();
	}

	@Override
	public ResponseDto verifyOnboardedMerchant(String email, String generatedPassword, String newPassword) {
		User user = new User();
		ResponseDto response = new ResponseDto();
		MerchantOnboard merchant = merchantOnboardRepository.findByEmail(email);
		String merchantEmail = merchant.getEmail();
		String password = merchant.getPassword();
		boolean matches = passwordEncoder.matches(generatedPassword, password);
		try {
			if (merchantOnboardRepository.existsByEmail(email) && email.equals(merchantEmail) && matches) {
				user.setFirstName("manjeet");
				user.setLastName("yadav");
				user.setMobile("6377049235");
				user.setUsername("yadavmanjeet");
				user.setAtemptCount(1);
				user.setEmail(merchantEmail);
				user.setStatus(true);
				user.setUnlocking(true);
				user.setPassword(passwordEncoder.encode(newPassword));
				User updated = userService.save(user);
				if (updated != null) {
					response.setMessage("password reset successfully");
					response.setStatus(StatusResponse.Success);
				} else {
					response.setMessage("Error in updating password");
					response.setStatus(StatusResponse.Failed);
				}
			} else {
				response.setMessage("Email password doesn't match!!");
				response.setStatus(StatusResponse.Failed);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Something went wrong!!");
			response.setStatus(StatusResponse.Failed);
		}
		return response;
	}

}
