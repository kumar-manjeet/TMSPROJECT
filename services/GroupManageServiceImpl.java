package com.tms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tms.models.Groups;
import com.tms.models.reponse.ResponseDto;
import com.tms.repositories.GroupRepository;
import com.tms.utils.StatusResponse;

@Service
public class GroupManageServiceImpl implements GroupManageService{
	
	@Autowired
	GroupRepository grpRepository;

	@Override
	public ResponseDto addGroups(Groups group) {
		ResponseDto response = new ResponseDto();
		try {
			Groups savedgroup =grpRepository.save(group);
			if(savedgroup!=null) {
				response.setMessage("Group added successfully");
				response.setData(savedgroup);
				response.setStatus(StatusResponse.Success);
			}
			else {
				response.setMessage("Error in adding group");
				response.setData(savedgroup);
				response.setStatus(StatusResponse.Failed);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Something went wrong !!");
			response.setStatus(StatusResponse.Failed);
		}
		return response;
		
	}

	@Override
	public ResponseDto getAllGroups() {
		ResponseDto response = new ResponseDto();
		try {
			List<Groups> allgroups = grpRepository.findAll();
			if(!allgroups.isEmpty()) {
				response.setMessage("Success");
				response.setData(allgroups);
				response.setStatus(StatusResponse.Success);
			}
			else {
				response.setMessage("Error in getting  group");
				response.setStatus(StatusResponse.Failed);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Something went wrong !!");
			response.setStatus(StatusResponse.Failed);
		}
		return response;
	}

}
