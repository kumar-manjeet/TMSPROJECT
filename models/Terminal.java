package com.tms.models;

import java.time.LocalDateTime;

import org.apache.poi.ss.usermodel.Row;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Terminal")
public class Terminal {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "refId", nullable=false)
	private Long refId;
	
	@Column(name = "terminalId", nullable=false)
	private Long terminalId;
	
	@Column(name = "model", nullable=false)
	private String model;
	
	@Column(name = "type", nullable=false)
	private String type;
	
	@Column(name = "iccId", nullable=false)
	private Long iccId;
	
	@Column(name = "imsi", nullable=false)
	private Long imsi;
	
	@Column(name = "currentRsi", nullable=false)
	private Long currentRsi;
	
	@Column(name = "firmwareVersion", nullable=false)
	private String firmwareVersion;
	
	@Column(name = "enrollDate", nullable=false)
	@CreationTimestamp
	private LocalDateTime enrollDate;
	
	@Column(name = "status", nullable=false)
	private int status;
	
	@Column(name = "batteryStatus", nullable=false)
	private int batteryStatus;
	
	@Column(name = "owner", nullable=false)
	private String owner;
	
	public Terminal(Row row) {
		
	}
	
	public Terminal() {
		
	}
	

}
