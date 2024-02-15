package com.tms.models;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
public class MerchantOnboard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String merchantName;

	@Column(name = "mobile_number", nullable = false)
	private String mobileNumber;

	@Column(name = "product_type", nullable = false)
	private String productType;

	@Column(name = "adhar_number", nullable = false)
	private String adharNumber;

	@Column(name = "pan_number", nullable = false)
	private String panNumber;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "bank_account_number", nullable = false)
	private String bankAccountNumber;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "password")
	private String password;

	@Column(name = "merchant_image", nullable = false)
	private String merchantImage;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@PrePersist
	protected void onCreate() {
		this.status = "initiated";
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "documents_id", referencedColumnName = "id")
	private Documents documents;
}
