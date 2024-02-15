package com.tms.models;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Complain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "terminal_id")
	private String terminalId;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "name")
	private String name;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "origin")
	private String origin;

	@Column(name = "product")
	private String product;

	@Column(name = "complain_taken_by")
	private String complainTakenBy;

	@Column(name = "complain_date")
	private LocalDateTime complainDate;

	@Column(name = "follow_up_date")
	private LocalDateTime followUpDate;

	@Column(name = "status")
	private String status;

	@Column(name = "address")
	private String address;

	@Column(name = "description")
	private String description;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;
	
	@JsonIgnore
	@OneToMany(mappedBy="complain", cascade=CascadeType.ALL)
	private List<ComplainRemark> complainRemark;
	
	@Column(name="CaseNo.")
	private Long caseNum;

}
