package com.tms.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "terminalAddressInfo", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"terminalId"}),
		@UniqueConstraint(columnNames = {"ipAddress"})
})
@Getter
@Setter
public class TerminalAddressInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "terminalId", nullable=false)
	private Long terminalId;
	
	@Column(name = "version", nullable=false)
	private String version;
	
	@Column(name = "ipAddress", nullable=false)
	private String ipAddress;
	
	@Column(name = "createdDate", nullable=false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name = "modifiedDate", nullable=false)
	@UpdateTimestamp
	private LocalDateTime modifiedDate;
	
	@Column(name = "ownerName", nullable=false)
	private String ownerName;
	
	@Column(name = "status", nullable=false)
	private boolean status;
	
	@Column(name = "address", nullable=false)
	private String address;
	
	@Column(name = "streetAddress", nullable=false)
	private String streetAddress;
	
	@Column(name = "apartment", nullable=false)
	private String apartment;
	
	@Column(name = "city", nullable=false)
	private String city;
	
	@Column(name = "state", nullable=false)
	private String state;
	
	@Column(name = "pincode", nullable=false)
	private double pincode;
	

}
