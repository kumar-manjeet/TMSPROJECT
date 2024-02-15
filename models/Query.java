package com.tms.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Query")
@Data
public class Query {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "terminal_id", insertable = false, updatable = false)
	private String terminalId;

	@Column(name = "query_type")
	private String queryType;

	@Column(name = "channel")
	private String channel;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "priority_group")
	private String priorityGroup;

	@Column(name = "status")
	private String status;

	@Column(name = "email")
	private String email;

	@Column(name = "description")
	private String description;

	@Column(name = "CaseNo.")
	private Long caseNum;

	@OneToMany(mappedBy = "query", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<QueryRemark> remarks = new ArrayList<>();

	public void addRemark(QueryRemark remark) {
		remarks.add(remark);
		remark.setQuery(this);
	}

}
