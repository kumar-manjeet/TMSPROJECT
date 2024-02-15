package com.tms.services;

import com.tms.models.Employee;
import com.tms.models.reponse.ResponseDto;

public interface EmployeeService {
	
	public ResponseDto saveEmployee(Employee employee);
	
	public ResponseDto approve(Long id,String status);

	public ResponseDto decline(Long id,String status);

}
