package com.tms.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "terminalId", nullable=false)
	private Long terminalId;
	
	@Column(name = "itemCode", nullable=false)
	private Long itemCode;
	
	@Column(name = "productName", nullable=false)
	private String productName;
	
	
	@Column(name = "createdDate", nullable=false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name = "modifiedDate", nullable=false)
	@UpdateTimestamp
	private LocalDateTime modifiedDate;
	
	@Column(name = "quantity", nullable=false)
	private int quantity;
	
	@Column(name = "costPerUnit", nullable=false)
	private double costPerUnit;
	
	@Column(name = "pricePerUnit", nullable=false)
	private double pricePerUnit;
	
	@Column(name = "totalCost", nullable=false)
	private double totalCost;

	
	
	

}
