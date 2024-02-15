package com.tms.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tms.models.Role;
import com.tms.models.User;
import com.tms.models.reponse.ResponseDto;
import com.tms.models.request.CreateUserRequestDto;
import com.tms.repositories.PermissionRepository;
import com.tms.repositories.RoleRepository;
import com.tms.repositories.UserRepository;
import com.tms.utils.StatusResponse;
import com.tms.vo.ForgotPAssword;
import com.tms.vo.ResetPasswordRequestVo;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	@Autowired
	EmailSendService emailservice;

	@Override
	public List<String> findRoleByUserName(String username) {
		List<String> roleNames = new ArrayList<>();
		try {
			User user = userRepository.findByUsername(username);
			if (user != null) {
				Set<Role> roles = user.getRoles();
				Iterator<Role> iterator = roles.iterator();
				while (iterator.hasNext()) {
					roleNames.add(iterator.next().getName());
				}
				return roleNames;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseDto saveUser(CreateUserRequestDto request) {
		ResponseDto response = new ResponseDto();
		try {
			User savedUser = userService.saveUser(request);
			if (savedUser != null && savedUser.getId() != null) {
				response.setMessage("User regestered successfully");
				response.setStatus(StatusResponse.Success);
			} else {
				response.setMessage("User not regestered !!");
				response.setStatus(StatusResponse.Failed);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Something went wrong");
			response.setStatus(StatusResponse.Failed);
		}
		return response;
	}

	@Override
	public List<String> findAllRoles() {
		List<String> roles = new ArrayList<String>();
		try {
			List<Role> allRole = roleRepository.findAll();
			if (!allRole.isEmpty()) {
				for (Role role : allRole) {
					roles.add(role.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}

	
	
	
	//implemnt otp generation and sent on mail
	@Override
	public ResponseDto forgotPassword(ForgotPAssword req) {
		ResponseDto response = new ResponseDto();
		String email = req.getEmail();
		try {
			
		      String subject = "regarding forgot password";
		      String message = "click this link to reset password   http://localhost:9000/login/resetPassword";
		    
		      
		      emailservice.toSendEmail(email, subject, message);
		      
		      response.setMessage("mail sent successfully");
				response.setStatus(StatusResponse.Success);
		}
		catch(Exception e ) {
			e.printStackTrace();
			response.setMessage("Something went wrong!!");
			response.setStatus(StatusResponse.Failed);
		}		
		return response;
	}
	
	@Override
	public ResponseDto resetPassword(ResetPasswordRequestVo  request) {
		ResponseDto response = new ResponseDto();
		String email = request.getEmail();
		String password = request.getPassword();
		try {
			if(userRepository.existsByEmail(email) || userRepository.existsByUsername(email)) {
				User user = userRepository.findByUsernameOrEmail(email,email);
				user.setPassword(encoder.encode(password));
				User updated = userService.save(user);
				if(updated!=null) {
					response.setMessage("password reset successfully");
					response.setStatus(StatusResponse.Success);
				}
				else {
					response.setMessage("Error in updating password");
					response.setStatus(StatusResponse.Failed);
				}
			}
			else {
				response.setMessage("Error in finding this mail");
				response.setStatus(StatusResponse.Failed);
			}
			
		}
		catch(Exception e ) {
			e.printStackTrace();
			response.setMessage("Something went wrong!!");
			response.setStatus(StatusResponse.Failed);
			
		}
		return response;
		
	}

}
