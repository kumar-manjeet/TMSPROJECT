package com.tms.services;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;

import com.tms.models.reponse.ResponseDto;
import com.tms.models.request.CreateUserRequestDto;
import com.tms.vo.ForgotPAssword;
import com.tms.vo.ResetPasswordRequestVo;

public interface LoginService {

	List<String> findRoleByUserName(String username);

	ResponseDto saveUser(CreateUserRequestDto request);

	List<String> findAllRoles();
	
	public ResponseDto forgotPassword(ForgotPAssword email);
	
	public ResponseDto resetPassword(ResetPasswordRequestVo request);
	
}
