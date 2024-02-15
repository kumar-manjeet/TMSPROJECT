package com.tms.restController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tms.models.Complain;
import com.tms.models.ComplainRemark;
import com.tms.models.Query;
import com.tms.models.QueryRemark;
import com.tms.models.reponse.ResponseDto;
import com.tms.models.request.ComplainRemarkDto;
import com.tms.models.request.CaseDto;
import com.tms.services.CasesService;
import com.tms.utils.StatusResponse;
import com.tms.vo.ComplainVo;
import com.tms.vo.FeedbackVo;
import com.tms.vo.QueryVo;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;

@RestController
@RequestMapping("/cases")
public class CasesController {

	@Autowired
	private CasesService casesService;

	@Value("${upload.path}")
	private String uploadPath;
	
	private DecimalFormat sixDigitFormat = new DecimalFormat("000000");

	@PostMapping("/saveQuery")
	public ResponseDto queryCases(@RequestBody QueryVo queryvo) {
		ResponseDto response = new ResponseDto();
		try {
			if (queryvo.getTerminalId() == null) {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Terminal id can't be null !!");
				return response;
			}
			if (queryvo.getStatus() == null) {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Status can't be empty !!");
				return response;
			}
			Long saveQuery = casesService.saveQuery(queryvo);
			if (saveQuery != null) {
				response.setStatus(StatusResponse.Success);
				response.setMessage("Query added successfully");
			} else {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("something went wrong");
			}
		} catch (Exception e) {
			response.setStatus(StatusResponse.Failed);
			response.setMessage("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@PostMapping("/saveComplain")
	public ResponseDto complainCases(@RequestBody ComplainVo complain) {
		ResponseDto response = new ResponseDto();
		try {
			if (complain.getTerminalId() == null) {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Terminal id can't be null !!");
				return response;
			}
			if (complain.getStatus() == null) {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Status can't be empty !!");
				return response;
			}
			Long saveComplain = casesService.saveComplain(complain);
			if (saveComplain != null) {
				response.setStatus(StatusResponse.Success);
				response.setMessage("Complain added successfully");
				return response;
			} else {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Something went wrong");
				return response;
			}
		} catch (Exception e) {
			response.setStatus(StatusResponse.Failed);
			response.setMessage("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@PostMapping("/saveFeedback")
	public ResponseDto feedbackCases(@RequestBody FeedbackVo feedback) {
		ResponseDto response = new ResponseDto();
		try {
			if (feedback.getTerminalId() == null) {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Terminal id can't be empty !!");
				return response;
			}
			if (feedback.getCategory() == null) {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Category can't be empty !!");
				return response;
			}
			Long saveFeedback = casesService.saveFeedback(feedback);
			if (saveFeedback != null) {
				response.setStatus(StatusResponse.Success);
				response.setMessage("Feedback added successfully");
			} else {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Something went wrong");
			}
		} catch (Exception e) {
			response.setStatus(StatusResponse.Failed);
			response.setMessage("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@PostMapping("/pendingQuery")
	public ResponseDto getPendingQuery(@RequestParam String status) {
		ResponseDto response = new ResponseDto();
		try {
			if (status.equalsIgnoreCase("pending") || status.equalsIgnoreCase("hold")
					|| status.equalsIgnoreCase("resolved")) {
				List<Query> pendingQuery = casesService.getPendingQuery(status);
				if (pendingQuery.size() == 0) {
					response.setStatus(StatusResponse.Data_Not_Found);
					response.setMessage("Data is not present !!");
					return response;
				}
				response.setStatus(StatusResponse.Success);
				response.setMessage("query received !!");
				response.setData(pendingQuery);
				return response;
			} else {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("enter a valid status !!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@PostMapping("/pendingComplain")
	public ResponseDto getPendingcomplain(@RequestParam String status) {
		ResponseDto response = new ResponseDto();
		try {
			if (status == null) {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("provide status !!");
				return response;
			}
			if (status.equalsIgnoreCase("pendig") || status.equalsIgnoreCase("hold")
					|| status.equalsIgnoreCase("resolved")) {
				List<Complain> pendingComplain = casesService.getPendingComplain(status);
				if (pendingComplain.size() == 0) {
					response.setStatus(StatusResponse.Data_Not_Found);
					response.setMessage("Data is not present !!");
					return response;
				}
				response.setStatus(StatusResponse.Success);
				response.setMessage("complain received successfully !!");
				response.setData(pendingComplain);
				return response;
			} else {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("enter a valid status !!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@PutMapping("/editQuery")
	public ResponseDto updateQuery(@RequestParam String queryId, @RequestBody QueryVo updatedQuery) {
		ResponseDto response = new ResponseDto();
		try {
			if (queryId != null && casesService.isexistsTerminalId(queryId)) {
				casesService.updateQuery(queryId, updatedQuery);
				response.setStatus(StatusResponse.Success);
				response.setMessage("Query successfully updated !!");
				return response;
			} else {
				response.setStatus(StatusResponse.Data_Not_Found);
				response.setMessage("id is not available !!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@PostMapping("/searchQuery")
	public ResponseDto searchBasedOnParam(@RequestParam(required = false) String terminalId,
			@RequestParam(required = false) String mobileNumber, @RequestParam(required = false) String email) {
		ResponseDto response = new ResponseDto();
		if (terminalId != null) {
			if (casesService.isexistsTerminalId(terminalId)) {
				List<Query> queryByTerminalId = casesService.getByTerminalId(terminalId);
				response.setStatus(StatusResponse.Success);
				response.setMessage("query received !!");
				response.setData(queryByTerminalId);
				return response;
			} else {
				response.setStatus(StatusResponse.Data_Not_Found);
				response.setMessage("terminal id does not exist");
				return response;
			}
		} else if (mobileNumber != null) {
			if (casesService.isexistMobileNumber(mobileNumber)) {
				List<Query> queryByMobileNumber = casesService.getByMobileNumber(mobileNumber);
				response.setStatus(StatusResponse.Success);
				response.setMessage("query received !!");
				response.setData(queryByMobileNumber);
				return response;
			} else {
				response.setStatus(StatusResponse.Data_Not_Found);
				response.setMessage("mobile number does not exists");
				return response;
			}
		} else if (email != null) {
			if (casesService.isexistsEmail(email)) {
				List<Query> queryByEmail = casesService.getByEmail(email);
				response.setStatus(StatusResponse.Success);
				response.setMessage("query received !!");
				response.setData(queryByEmail);
				return response;
			} else {
				response.setStatus(StatusResponse.Success);
				response.setMessage("email does't exist !!");
				return response;
			}
		}
		return response;
	}


	@PostMapping("/addComplainRemark")
	public ResponseDto saveComlainRemark(@ModelAttribute ComplainRemarkDto complainRemarkDto) {
		ComplainRemark complainRemark = new ComplainRemark();
		ResponseDto response = new ResponseDto();
		try {
			String remark = complainRemarkDto.getRemark();
			String fileName = StringUtils.cleanPath(complainRemarkDto.getFile().getOriginalFilename());
			Path uploadLocation = Paths.get(uploadPath);

			if (!Files.exists(uploadLocation)) {
				Files.createDirectories(uploadLocation);
			}

			Path filePath = uploadLocation.resolve(fileName);
			Files.copy(complainRemarkDto.getFile().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			String imageUrl = "/images/" + fileName;
			complainRemark.setImageUrl(imageUrl);
			complainRemark.setRemark(remark);
			complainRemark.setImageName(fileName);

			Long saveComplainRemark = casesService.saveComplainRemark(complainRemark);
			if (saveComplainRemark != null) {
				response.setData(StatusResponse.Success);
				response.setMessage("Complain remark added successfully");
				return response;
			} else {
				response.setStatus(StatusResponse.Failed);
				response.setMessage("Something went wrong");
				return response;
			}
		} catch (IOException e) {
			response.setStatus(StatusResponse.Failed);
			response.setMessage("Something went wrong" + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}


	@PostMapping("/searchCases")
	public ResponseDto getCombinedCases() {
		ResponseDto response = new ResponseDto();
		try {
			List<CaseDto> combinedData = casesService.findBoth();
			if (combinedData != null) {
				response.setStatus(StatusResponse.Success);
				response.setMessage("Cases received successfully !!");
				response.setData(combinedData);
				return response;
			} else {
				response.setStatus(StatusResponse.Data_Not_Found);
				response.setMessage("Something went wrong");
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusResponse.Failed);
			response.setMessage("Something went wrong");

		}

       return response; 
    }
	
	@PostMapping("/addRemark")
	public ResponseDto addRemark(@RequestBody ComplainRemarkDto remark) {
		
		ResponseDto response = new ResponseDto();
//		Long id = remark.getIdForComplain();
//		String remarkMessage = remark.getRemark();
		ComplainRemark complain = casesService.addOrUpdateRemarkToComplaint(remark);
		 if(complain!=null) {
	        	response.setStatus(StatusResponse.Success);
				response.setMessage("complain received successfully !!");
				response.setData(complain);
				return response;
	        }
	        else {
	        	response.setStatus(StatusResponse.Data_Not_Found);
				response.setMessage("Something went wrong");
				return response;
	        }	
	}
	
	@GetMapping("/getCaseNumber")
	public ResponseDto getCaseNumber() {
		ResponseDto response = new ResponseDto();
		try {
			Long number = casesService.generateCaseNumber();
			String formattedNumber = sixDigitFormat.format(number);
			response.setStatus(StatusResponse.Success);
			response.setMessage("Case Number received successfully !!");
			response.setData(formattedNumber);
			return response;
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusResponse.Failed);
			response.setMessage("Something went wrong");
			return response;
		}

	}

	@PostMapping("/addQueryRemark")
	public ResponseDto addRemarkToQuery(@RequestBody QueryRemark remark) {
		ResponseDto response = new ResponseDto();
		Long savedQueryRemarkId = casesService.saveQueryRemark(remark);
		if (savedQueryRemarkId != null) {
			response.setStatus(StatusResponse.Success);
			response.setMessage("query remark added successfully!!");
			return response;
		} else {
			response.setStatus(StatusResponse.Failed);
			response.setMessage("Something went wrong !!");
			return response;
		}
	}
	
	// controller code for add remark to query
	
//	@PostMapping("/saveQuery/query")
//    public Query createQuery(@RequestBody Query query) {
//        return casesService.saveQuery(query);
//    }

}
