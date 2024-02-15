package com.tms.restController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tms.config.JwtUtil;
import com.tms.models.Permission;
import com.tms.models.Role;
import com.tms.models.User;
import com.tms.models.reponse.LoginResponseDto;
import com.tms.models.reponse.ResponseDto;
import com.tms.models.request.CreateUserRequestDto;
import com.tms.models.request.LoginRequestDto;
import com.tms.repositories.RoleRepository;
import com.tms.repositories.UserRepository;
import com.tms.services.CustomUserDetailService;
import com.tms.services.LoginService;
import com.tms.services.UserService;
import com.tms.utils.StatusResponse;
import com.tms.vo.ForgotPAssword;
import com.tms.vo.ResetPasswordRequestVo;

@RestController
@RequestMapping("/login")
public class LoginRestController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Autowired
	 RestTemplate restTemplate;
	
//	@Autowired
//	private MerchantService merchantService;

	@PostMapping("/signin")
	public @ResponseBody ResponseDto authenticateUser(@RequestBody LoginRequestDto request) {
		ResponseDto response = new ResponseDto();
		LoginResponseDto loginResponse = new LoginResponseDto();
		String token = null;
		try {
			User user = userRepository.findByUsernameOrEmail(request.getUsername(), request.getUsername());
			if (user.getAtemptCount() < 6) {
				user.setAtemptCount(user.getAtemptCount() + 1);
				if (user.getAtemptCount() > 5) {
					user.setUnlocking(false);
				}
				userService.save(user);
			}

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getUsername());
			token = this.jwtUtil.generateToken(userDetails);
			List<String> roles = loginService.findRoleByUserName(userDetails.getUsername());

			// reseting user atempts
			user.setAtemptCount(0);
			user.setUnlocking(true);
			user.setLastUsed(LocalDateTime.now());
			userService.save(user);

//			Merchant merchant=merchantService.findByUser(user);
//			if(merchant != null) {
//				loginResponse.setmId(String.valueOf(merchant.getId()));
//			}
			loginResponse.setToken(token);
			loginResponse.setRoles(roles);
			loginResponse.setUserName(userDetails.getUsername());
			response.setData(loginResponse);
			response.setMessage("Logged In");
			response.setStatus(StatusResponse.Success);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setStatus(StatusResponse.Failed);
		}
		return response;
	}

	@PostMapping("/createUser")
	public @ResponseBody ResponseDto registerUser(@RequestBody CreateUserRequestDto request) {
		ResponseDto response = new ResponseDto();
		try {

			if (userService.existsByUsername(request.getUserName())) {
				response.setMessage("Username is already taken!");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			if (userService.existsByEmail(request.getEmail())) {
				response.setMessage("Email is already in use!");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			if (userService.existsByMobile(request.getMobile())) {
				response.setMessage("Mobile no is already in use!");
				response.setStatus(StatusResponse.Failed);
				return response;
			}

			response = loginService.saveUser(request);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Something went wrong!");
			response.setStatus(StatusResponse.Failed);
		}
		return response;
	}
	
	@PostMapping("/verifyUser")
	public @ResponseBody ResponseDto registerUserByUserName(@RequestBody CreateUserRequestDto request) {
		ResponseDto response = new ResponseDto();
		try {

			if (userService.existsByUsername(request.getUserName())) {
				response.setMessage("Username is already taken!");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			response = loginService.saveUser(request);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Something went wrong!");
			response.setStatus(StatusResponse.Failed);
		}
		return response;
	}
	
	@PostMapping("/getRoles")
	public @ResponseBody ResponseDto getRoles() {
		ResponseDto response = new ResponseDto();
		List<String> roles = new ArrayList<String>();
		try {
			roles=loginService.findAllRoles();
			if(roles.isEmpty()) {
				response.setMessage("Data not Found");
				response.setStatus(StatusResponse.Data_Not_Found);
			}else {
				response.setData(roles);
				response.setMessage("Data Found");
				response.setStatus(StatusResponse.Success);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Something went wrong!");
			response.setStatus(StatusResponse.Failed);
		}
		return response;
	}

	@PostMapping("/resetPassword")
	public @ResponseBody ResponseDto resetPassword(@RequestBody ResetPasswordRequestVo request) {
		ResponseDto response = new ResponseDto();
		try {
			if (request.getEmail() == null || request.getEmail().equals("")) {
				response.setMessage("EmailOrUsername is mendatory field !");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			if (request.getPassword() == null || request.getPassword().equals("")) {
				response.setMessage("Password is mendatory field !!");
				response.setStatus(StatusResponse.Failed);
				return response;
			}

			response = loginService.resetPassword(request);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Something went wrong!");
			response.setStatus(StatusResponse.Failed);
			return response;
		}
		return response;
	}
	
	@PostMapping("/forgotPassword")
	public @ResponseBody ResponseDto forgotPassword(@RequestBody ForgotPAssword req) {
		ResponseDto response = new ResponseDto();
		String email = req.getEmail();
		try {
			if(email.equals(null) || email.isEmpty()) {
				response.setMessage("EmailOrUsername is mendatory field !");
				response.setStatus(StatusResponse.Failed);
				return response;

			}
			response = loginService.forgotPassword(req);
			
		}catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Something went wrong!");
			response.setStatus(StatusResponse.Failed);
			return response;
		}
		return response;
			
		}
	
	
	
//	@PostMapping("/getRolePermission")
//	public @ResponseBody ResponseDto getRolePermission(@RequestParam String roleName) {
//		ResponseDto response = new ResponseDto();
//		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
//		try {
//			Role role = roleRepository.findByName(roleName);
//			for(Permission data : role.getPermissions()) {
//				if(map.get(data.getModule())!=null) {
//					Set<String> set = map.get(data.getModule());
//					set.add(data.getPermissionName());
//					map.put(data.getModule(), set);
//				}else {
//					Set<String> set = new HashSet<>();
//					set.add(data.getPermissionName());
//					map.put(data.getModule(), set);
//				}
//			}
//			if(map.isEmpty()) {
//				response.setStatus(StatusResponse.Data_Not_Found);
//				response.setMessage("No permission Assigned");
//			}else {
//				response.setStatus(StatusResponse.Success);
//				response.setMessage("Data found successfully");
//				response.setData(map);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return response;
//	}

	@PostMapping("/getRolesPermissions")
	@Cacheable("apiResponseCache")
	public Object getRolesFromApi() {
		 
		 String api = "http://localhost:8080/roleInheritance?rolesList=Admin&product=tms";
	      ResponseEntity<Object> response = restTemplate.getForEntity(api, Object.class);
	        Object responseBody = response.getBody();
	        System.out.println(responseBody);
			return responseBody;
	}

}
