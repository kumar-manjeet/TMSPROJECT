package com.tms.services;

import java.util.List;

import com.tms.models.Complain;
import com.tms.models.ComplainRemark;
import com.tms.models.Query;
import com.tms.models.QueryRemark;
import com.tms.models.request.CaseDto;
import com.tms.models.request.ComplainRemarkDto;
import com.tms.vo.ComplainVo;
import com.tms.vo.FeedbackVo;
import com.tms.vo.QueryVo;

public interface CasesService {

	public Long saveQuery(QueryVo query);

	public Long saveComplain(ComplainVo complain);

	public Long saveFeedback(FeedbackVo feedback);

	public List<Query> getPendingQuery(String status);

	public List<Complain> getPendingComplain(String status);

	public Long updateQuery(String queryId, QueryVo updatedQuery);

	List<Query> getByTerminalId(String id);

	List<Query> getByMobileNumber(String mobileNumber);

	List<Query> getByEmail(String email);

	public Boolean isexistsTerminalId(String terminalId);

	public Boolean isexistMobileNumber(String mobile);

	public Boolean isexistsEmail(String email);
	
	public Long saveComplainRemark(ComplainRemark complainRemark);
  
	public List<CaseDto>  findBoth();

	public ComplainRemark addOrUpdateRemarkToComplaint(ComplainRemarkDto remark);
	
	public Long generateCaseNumber() ;

	public Complain addRemarkToComplaint(Long id,ComplainRemark complainRemark);
	
	public Long saveQueryRemark(QueryRemark queryRemark);
	
	public QueryRemark addRemarkToQuery(Long queryId, QueryRemark remark);


}
