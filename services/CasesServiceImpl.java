package com.tms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tms.models.Complain;
import com.tms.models.ComplainRemark;
import com.tms.models.Feedback;
import com.tms.models.Query;
import com.tms.models.QueryRemark;
import com.tms.repositories.ComplainRemarkRepository;
import com.tms.models.request.CaseDto;
import com.tms.models.request.ComplainRemarkDto;
import com.tms.repositories.ComplainRepository;
import com.tms.repositories.FeedbackRepository;
import com.tms.repositories.QueryRemarkRepository;
import com.tms.repositories.QueryRepository;
import com.tms.vo.ComplainVo;
import com.tms.vo.FeedbackVo;
import com.tms.vo.QueryVo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CasesServiceImpl implements CasesService {

	@Autowired
	private QueryRepository queryRepository;

	@Autowired
	private ComplainRepository complainRepository;

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private ComplainRemarkRepository complainRemarkRepository;

	@Autowired
	private QueryRemarkRepository queryRemarkRepository;

	@Override
	public Long saveQuery(QueryVo queryvo) {
		Query query = new Query();
		try {
			BeanUtils.copyProperties(queryvo, query);
			query = queryRepository.save(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query.getId();
	}

	@Override
	public Long saveComplain(ComplainVo complain) {
		Complain registerComplain = new Complain();
		try {
			BeanUtils.copyProperties(complain, registerComplain);
			registerComplain = complainRepository.save(registerComplain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return registerComplain.getId();

	}

	@Override
	public Long saveFeedback(FeedbackVo feedbackvo) {
		Feedback feedback = new Feedback();
		try {
			BeanUtils.copyProperties(feedbackvo, feedback);
			feedback = feedbackRepository.save(feedback);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return feedback.getId();
	}

	@Override
	public List<Query> getPendingQuery(String status) {
		try {
			return queryRepository.findByStatus(status);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Complain> getPendingComplain(String status) {
		try {
			return complainRepository.findByStatus(status);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Query> getByTerminalId(String id) {
		try {
			return queryRepository.findByTerminalId(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Query> getByMobileNumber(String mobileNumber) {
		try {
			return queryRepository.findByMobileNumber(mobileNumber);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Query> getByEmail(String email) {
		try {
			return queryRepository.findByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long updateQuery(String queryId, QueryVo updatedQuery) {
		Query query = new Query();
		try {
			BeanUtils.copyProperties(updatedQuery, query);
			List<Query> findByTerminalId = queryRepository.findByTerminalId(queryId);
			for (Query queryByID : findByTerminalId) {
				queryByID.setStatus(updatedQuery.getStatus());
				queryByID.setMobileNumber(updatedQuery.getMobileNumber());
				queryRepository.save(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query.getId();
	}

	@Override
	public Boolean isexistsTerminalId(String terminalId) {
		return queryRepository.existsByTerminalId(terminalId);
	}

	@Override
	public Boolean isexistMobileNumber(String mobile) {
		return queryRepository.existsByMobileNumber(mobile);
	}

	@Override
	public Boolean isexistsEmail(String email) {
		return queryRepository.existsByEmail(email);
	}

	@Override
	public Long saveComplainRemark(ComplainRemark complainRemark) {
		complainRemarkRepository.save(complainRemark);
		return complainRemark.getId();
	}

	public List<CaseDto> findBoth() {
		List<Query> queries = queryRepository.findAll();
		List<Complain> complaints = complainRepository.findAll();
		List<Feedback> feedbacks = feedbackRepository.findAll();
		List<CaseDto> combinedData = new ArrayList<>();
		for (Query query : queries) {
			CaseDto caseDto = new CaseDto();
			caseDto.setTermial_id(query.getTerminalId());
			caseDto.setEmail(query.getEmail());
			caseDto.setMobile(query.getMobileNumber());
			caseDto.setCategory("Query");
			combinedData.add(caseDto);
		}

		for (Complain complain : complaints) {
			CaseDto caseDto1 = new CaseDto();
			caseDto1.setTermial_id(complain.getTerminalId());
			caseDto1.setEmail(complain.getEmailId());
			caseDto1.setMobile(complain.getMobileNumber());
			caseDto1.setCategory("Complaint");
			combinedData.add(caseDto1);
		}

		for (Feedback feedback : feedbacks) {
			CaseDto caseDto1 = new CaseDto();
			caseDto1.setTermial_id(feedback.getTerminalId());
			caseDto1.setEmail(feedback.getEmail());
			caseDto1.setMobile(feedback.getMobile());
			caseDto1.setCategory("Feedback");
			combinedData.add(caseDto1);
		}

		return combinedData;
	}

	@Override
	public ComplainRemark addOrUpdateRemarkToComplaint(ComplainRemarkDto remark) {
	    Long complaintId = remark.getIdForComplain();
	    String msg = remark.getRemark();

	    Optional<ComplainRemark> existingRemarkOptional = complainRemarkRepository.findByComplainId(complaintId);
	    
	    if (existingRemarkOptional.isPresent()) {
	      
	        ComplainRemark existingRemark = existingRemarkOptional.get();
	        existingRemark.setRemark(msg);
	        return complainRemarkRepository.save(existingRemark);
	    } else {
	      
	        Complain complaint = complainRepository.findById(complaintId)
	                .orElseThrow(() -> new EntityNotFoundException("Complaint not found with id: " + complaintId));
	        ComplainRemark newRemark = new ComplainRemark();
	        newRemark.setRemark(msg);
	        complaint.getComplainRemark().add(newRemark);
	        newRemark.setComplain(complaint);
	        return complainRemarkRepository.save(newRemark);
	    }
	}
	
	Long queryCurrentValue = 000001L;

	
	@Override
	public Long generateCaseNumber() {
        return queryCurrentValue++;
    }
	

	public Complain addRemarkToComplaint(Long complaintId, ComplainRemark remark) {
		Complain complaint = complainRepository.findById(complaintId)
				.orElseThrow(() -> new EntityNotFoundException("Complaint not found with id: " + complaintId));
		remark.setComplain(complaint);
		complaint.getComplainRemark().add(remark);

		Complain complain = complainRepository.save(complaint);
		return complain;
	}

	@Override
	public Long saveQueryRemark(QueryRemark queryRemark) {
		QueryRemark savedRemark = queryRemarkRepository.save(queryRemark);
		return savedRemark.getId();
	}
	// service code for query remark 
	


	@Override
    public QueryRemark addRemarkToQuery(Long queryId, QueryRemark remark) {
        Query query = queryRepository.findById(queryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid query id: " + queryId));

//        QueryRemark remark = new QueryRemark();
        remark.setRemark(remark.getRemark());
//        remark.setData(remark.getData());
        query.addRemark(remark);

        return queryRemarkRepository.save(remark);
    }
	
	

}
