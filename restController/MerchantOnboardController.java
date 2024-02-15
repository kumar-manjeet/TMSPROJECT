package com.tms.restController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tms.models.MerchantOnboard;
import com.tms.models.reponse.ResponseDto;
import com.tms.models.request.CreateUserRequestDto;
import com.tms.models.request.MerchantOnboardDto;
import com.tms.services.EmailService;
import com.tms.services.MerchantOnboardService;
import com.tms.utils.StatusResponse;
import com.tms.vo.ResetMerchantPassword;
import com.tms.vo.ResetPasswordRequestVo;

@RestController
public class MerchantOnboardController {

	@Autowired
	private MerchantOnboardService merchantOnboardService;

	@Autowired
	private EmailService emailService;

	@PostMapping("/onboardMerchant")
	public ResponseDto onboardMerchant(@ModelAttribute MerchantOnboardDto merchantOnboardDto) {
		String generatedPassword = merchantOnboardService.generatePassword(5, true, true, true);
		ResponseDto response = new ResponseDto();
		String email = merchantOnboardDto.getEmail();
		String merchantName = merchantOnboardDto.getMerchantName();
		try {
			if (merchantOnboardDto.getMerchantName() == null) {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Merchant Name can't be null !!");
				return response;
			}
			if (merchantOnboardDto.getMobileNumber() == null) {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Mobile Number can't be empty !!");
				return response;
			}
			if (merchantOnboardDto.getAdharNumber() == null) {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Adhar Number can't be empty !!");
				return response;
			}
			if (merchantOnboardDto.getBankAccountNumber() == null) {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Account Number can't be empty !!");
				return response;
			}
			if (merchantOnboardService.existsByMerchantName(merchantOnboardDto.getMerchantName())) {
				response.setMessage("Merchant Name is already taken!");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			if (merchantOnboardService.existsByEmail(merchantOnboardDto.getEmail())) {
				response.setMessage("Email is already in use!");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			if (merchantOnboardService.existsByMobileNumber(merchantOnboardDto.getMobileNumber())) {
				response.setMessage("Mobile no is already in use!");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			Long merchantId = merchantOnboardService.merchantOnboard(merchantOnboardDto, generatedPassword);
			if (merchantId != null) {
				response.setStatus(StatusResponse.Success);
				response.setMessage("Merchant Onboarded successfully");
				emailService.sendSimpleMessage(email, merchantName, generatedPassword);
			} else {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("something went wrong");
			}
		} catch (Exception e) {
			response.setStatus(StatusResponse.Failed);
			response.setMessage("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/allOnboardMerchant")
	public ResponseDto allOnboardMerchant() {
		ResponseDto response = new ResponseDto();
		try {
			List<MerchantOnboard> onboardedMerchant = merchantOnboardService.getOnboardedMerchant();
			if (onboardedMerchant != null && onboardedMerchant.size() != 0) {
				response.setStatus(StatusResponse.Success);
				response.setMessage("Onboarded merchant received successfully");
				response.setData(onboardedMerchant);
			} else if (onboardedMerchant.size() == 0) {
				response.setStatus(StatusResponse.Data_Not_Found);
				response.setMessage("data not found");
			} else {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("something went wrong");
			}
		} catch (Exception e) {
			response.setStatus(StatusResponse.Failed);
			response.setMessage("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/merchantByID")
	public ResponseDto getMerchantById(@RequestParam Long merchantId) {
		ResponseDto response = new ResponseDto();
		Optional<MerchantOnboard> merchant = merchantOnboardService.getOnboardedMerchantById(merchantId);
		if (merchant != null && merchant.isPresent()) {
			response.setStatus(StatusResponse.Success);
			response.setMessage("merchant received successfully");
			response.setData(merchant);
			return response;
		} else {
			response.setStatus(StatusResponse.Data_Not_Found);
			response.setMessage("merchant is not available for this id!!");
			return response;
		}
	}

	@PostMapping("/resetMerchantPassword")
	public ResponseDto resetPassword(@RequestBody ResetMerchantPassword request) {
		ResponseDto response = new ResponseDto();
		try {
			if (request.getEmail() == null || request.getEmail().equals("")) {
				response.setMessage("EmailOrUsername is mendatory field !");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			if (request.getGeneratedPassword() == null || request.getGeneratedPassword().equals("")) {
				response.setMessage("Password is mendatory field !!");
				response.setStatus(StatusResponse.Failed);
				return response;
			}

			ResponseDto responseDto = merchantOnboardService.verifyOnboardedMerchant(request.getEmail(),
					request.getGeneratedPassword(), request.getNewPassword());
			return responseDto;
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Something went wrong!");
			response.setStatus(StatusResponse.Failed);
			return response;
		}
	}
}
