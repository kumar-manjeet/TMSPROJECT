package com.tms.models.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ComplainRemarkDto {

	private String remark;
	
	private Long idForComplain;

	private MultipartFile file;

}
