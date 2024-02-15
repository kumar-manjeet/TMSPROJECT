package com.tms.models.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MerchantOnboardDto {

	private String merchantName;

	private String mobileNumber;

	private String productType;

	private String adharNumber;
	
	private String panNumber;

	private String bankAccountNumber;
	
	private String email;

	private String address;
	
	private String status;
	
	private MultipartFile merchantImage;

	private MultipartFile adharcard;

	private MultipartFile pancard;
}
