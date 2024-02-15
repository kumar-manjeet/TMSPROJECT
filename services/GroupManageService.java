package com.tms.services;

import com.tms.models.Groups;
import com.tms.models.reponse.ResponseDto;

public interface GroupManageService {
	
	 ResponseDto addGroups(Groups group);
	 
	 ResponseDto getAllGroups();

}
