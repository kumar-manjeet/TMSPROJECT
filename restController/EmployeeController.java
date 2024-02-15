package com.tms.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tms.models.Employee;
import com.tms.models.reponse.ResponseDto;
import com.tms.repositories.EmployeeRepository;
import com.tms.services.EmployeeService;
import com.tms.utils.StatusResponse;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService empservice;
	
	@Autowired 
	EmployeeRepository emprepo;

	@PostMapping("/registerEmployee")
	public ResponseDto empApprove(@RequestBody Employee employee) {
		ResponseDto response = new ResponseDto();
		try {
			if (emprepo.existsByUsername(employee.getUsername())) {
				response.setMessage("Username is already taken!");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			if (emprepo.existsByEmail(employee.getEmail())) {
				response.setMessage("Email is already in use!");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			if (emprepo.existsByMobile(employee.getMobile())) {
				response.setMessage("Mobile no is already in use!");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			response = empservice.saveEmployee(employee);

		}catch(Exception e) {
			e.printStackTrace();
			response.setMessage("Something went wrong!");
			response.setStatus(StatusResponse.Failed);
		}

		return response;

	}

	@PostMapping("/status")
	public ResponseDto empApprove(@RequestParam Long id,@RequestParam String status) {
		ResponseDto response = new ResponseDto();
		try {
			if(id.equals(null)){
				response.setMessage("Provide valid id");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			if(status.isEmpty()) {
				response.setMessage("Provide valid status for approval");
				response.setStatus(StatusResponse.Failed);
				return response;
			}
			response = empservice.approve(id,status);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setMessage("Something went wrong!");
			response.setStatus(StatusResponse.Failed);
		}
		return response;

	}

}
