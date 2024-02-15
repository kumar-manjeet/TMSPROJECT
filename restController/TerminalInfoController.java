package com.tms.restController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tms.exceptions.DeviceNotRegisteredException;
import com.tms.exceptions.TerminalAlreadyExistException;
import com.tms.exceptions.TerminalNotRegisteredException;

import com.tms.models.TerminalAddressInfo;
import com.tms.models.reponse.ErrorRes;
import com.tms.models.reponse.ResponseDto;
import com.tms.models.request.TerminalInfoReq;
import com.tms.repositories.TerminalRepository;
import com.tms.services.TerminalAddressService;
import com.tms.services.TerminalService;
import com.tms.utils.StatusResponse;
import com.tms.vo.TerminalVo;

@RestController
@RequestMapping("/terminalInfo")
public class TerminalInfoController {

	@Autowired
	TerminalAddressService infoService;

	@Autowired
	TerminalService deviceService;

	@Autowired
	TerminalRepository repo;

	@PostMapping("/addTerminalAddress")
	public ResponseDto registerTerminal(@RequestBody TerminalInfoReq request) {
		ResponseDto response = new ResponseDto();
		if (request.getTerminalId() != null) {
			if (infoService.existsByTerminalId(request.getTerminalId())) {
				throw new TerminalAlreadyExistException("Terminal Id already exists");
			}
			if (infoService.existsByIpAddress(request.getIpAddress())) {
				throw new TerminalAlreadyExistException("Terminal ip address already exists");
			}

			response = infoService.save(request);
		} else {
			throw new TerminalNotRegisteredException("terminal id is null");
		}
		return response;
	}

	@PostMapping("/addTerminal")
	public ResponseDto registerDevice(@RequestBody TerminalVo devicerequest) {
		ResponseDto response = new ResponseDto();
		if (devicerequest.getRefId() != null && devicerequest.getIccId() != null && devicerequest.getImsi() != null
				&& devicerequest.getCurrentRsi() != null) {
			if (deviceService.existsByRefId(devicerequest.getRefId())) {
				throw new DeviceNotRegisteredException("refid already exists");
			}
			if (deviceService.existsByIccId(devicerequest.getIccId())) {
				throw new DeviceNotRegisteredException("icc id already exists");
			}
			if (deviceService.existsByImsi(devicerequest.getImsi())) {
				throw new DeviceNotRegisteredException("imsi id already exists");
			}
			if (deviceService.existsByCurrentRsi(devicerequest.getCurrentRsi())) {
				throw new DeviceNotRegisteredException("current rsi id already exists");
			}
			response = deviceService.save(devicerequest);
		} else {

			throw new DeviceNotRegisteredException("ids can't be null");
		}
		return response;
	}

	@PostMapping("/findTerminalAddress")
	public ResponseEntity<Object> findfTerminalAddressById(@RequestParam Long terminalId) {
		try {
			if (terminalId == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("terminalId is null");
			}
			TerminalAddressInfo info = infoService.findTerminalBYId(terminalId);
			return ResponseEntity.ok(info);
		} catch (Exception e) {
			ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	@PostMapping("/terminaldetails")
	public ResponseDto findfTerminalById(@RequestParam Long terminalId) {
		ResponseDto response = new ResponseDto();
		try {
			if (terminalId == null) {
				response.setMessage("terminal id is null");
				response.setStatus(StatusResponse.Data_Not_Found);
			}
			response = deviceService.searchDevice(terminalId);
			return response;
		} catch (Exception e) {

			e.printStackTrace();
			response.setMessage("Something went wrong!");
			response.setStatus(StatusResponse.Failed);
		}
		return response;
	}

	@PostMapping("/getterminals")
	public ResponseDto getTerminals() {
		ResponseDto response = new ResponseDto();
		List<Long> terminals = new ArrayList<Long>();
		try {
			terminals = deviceService.getTerminals();
			if (terminals.isEmpty()) {
				response.setMessage("Data not Found");
				response.setStatus(StatusResponse.Data_Not_Found);
			} else {
				response.setData(terminals);
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

	@PostMapping("/upload")
	public ResponseDto uploadFile(@RequestParam("file") MultipartFile file) {
		ResponseDto response = new ResponseDto();
	    try {
	    	if(!file.isEmpty()) {
	    	int uploadFile = deviceService.uploadFile(file);
	    	if(uploadFile == 1) {
	    		response.setMessage("File uploaded successfully");
	    		response.setStatus(StatusResponse.Success);
	    		return response;
	    	}
	    	else {
	    		response.setMessage("Failed to upload file");
	    		response.setStatus(StatusResponse.Failed);
	    		return response;
	    	}
	    	}
	    	return response;
	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	response.setMessage("Something went wrong"+ e.getMessage());
	    	response.setStatus(StatusResponse.Failed);
	        return response;
	    }
	}

}
