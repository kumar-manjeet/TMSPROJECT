package com.tms.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tms.models.Employee;
import com.tms.models.User;
import com.tms.models.reponse.ResponseDto;
import com.tms.repositories.EmployeeRepository;
import com.tms.repositories.UserRepository;
import com.tms.utils.StatusResponse;
import com.tms.vo.EmployeeVo;
import com.tms.vo.UserVo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository emprepo;

	@Autowired
	UserRepository userrepo;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JavaMailSender mailSender;
	
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private EmailSendService emailService;

	@Override
	public ResponseDto saveEmployee(Employee employee) {

		ResponseDto response = new ResponseDto();
		try {
			String pass = employee.getPassword();
			employee.setPassword(encoder.encode(pass));
			Employee savedUser = emprepo.save(employee);
			EmployeeVo emp = this.modelMapper.map(savedUser, EmployeeVo.class); 
			if (savedUser != null && savedUser.getId() != null) {
				response.setMessage("Employee registered successfully");
				response.setStatus(StatusResponse.Success);
				response.setData(emp);
			} else {
				response.setMessage("Employee not registered !!");
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
	public ResponseDto approve(Long id,String status) {
		ResponseDto response = new ResponseDto();
		if(!emprepo.existsById(id)) {
			response.setMessage("id not exists");
			  response.setStatus(StatusResponse.Failed);
			  return response;
		}
	   Optional<Employee> emp = emprepo.findById(id);
	  Employee em =  emp.get();
	  String email = em.getEmail();
	  String mobile = em.getMobile();
	  String firstname = em.getFirstName();
	  String lastname = em.getLastName();
	  String uname = em.getUsername();
	  String pass = em.getPassword();
	  try {
		  String subject = "Regarding Approval of Tms User";
		  String message = "Hi " + firstname+ " You are a authentic user of TMS";
		  emailService.toSendEmail(email, subject, message);

	  User user = new User();
	  user.setEmail(email);
	  user.setFirstName(firstname);
	  user.setLastName(lastname);
	  user.setMobile(mobile);
	  user.setUsername(uname);
	  user.setPassword(encoder.encode(pass));
	  user.setAtemptCount(0);
	  user.setStatus(true);
	  user.setUserType("employee");
	  user.setUnlocking(true);
	  em.setStatus(status);
	  
	  
	  User user1 =userrepo.save(user);
	  emprepo.save(em);
	  UserVo uservo = this.modelMapper.map(user1, UserVo.class); 
	  if(user1!=null) {
		  response.setMessage("Mail Sent Successfully And User Registered also.");
		  response.setData(uservo);
		  response.setStatus(StatusResponse.Success);
		  return response;
	  }
	  else {
		  response.setMessage("Employee data is null");
		  response.setStatus(StatusResponse.Failed);
	  }
	  }
	  catch(Exception e) {
		  e.printStackTrace();
		  response.setMessage("Error in processing request");
		  response.setStatus(StatusResponse.Failed);
	  }
	return response;
		  
	}

	@Override
	public ResponseDto decline(Long id,String status) {
		ResponseDto response = new ResponseDto();
	   Optional<Employee> emp = emprepo.findById(id);
	  Employee em =  emp.get();
	  String email = em.getEmail();
	  try {
 
	  SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(email);
      message.setSubject("Regarding Approval of Tms User");
      message.setText("Your request has been declined !!");

      mailSender.send(message);
	}
	  catch(Exception e) {
		  e.printStackTrace();
		  response.setMessage("Error in processing request");
		  response.setStatus(StatusResponse.Failed);
	  }
	  return response;
	  
	}
	
	
}