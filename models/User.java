package com.tms.models;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "user", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"username"})})
//		@UniqueConstraint(columnNames = {"email"})
//
public class User {
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name", nullable=false)
	private String firstName;
	
	@Column(name = "last_name", nullable=false)
	private String lastName;
	
	@Column(name = "email", nullable=false)
	private String email;
	
	@Column(name = "mobile", nullable=false)
	private String mobile;
	
	@Column(name = "username", nullable=false)
	private String username;
	
	@Column(name = "password", nullable=false)
	private String password;
	
	@Column(name = "unlocking", nullable=false)
	private Boolean unlocking;
	
	@Column(name = "atempt_count", nullable=false)
	private Integer atemptCount;
	
	@Column(name = "last_used", nullable=false)
	@CreationTimestamp
	private LocalDateTime lastUsed;
	
	@Column(name = "status", nullable=false)
	private Boolean status;
	
	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;
	
	@Column(name = "userType")
	private String userType;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="user_roles", 
	joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
	private Set<Role>roles;

	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="user_groups", 
	joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name="group_id", referencedColumnName = "id"))
	private Set<Groups> groups;

	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Boolean getUnlocking() {
		return unlocking;
	}


	public void setUnlocking(Boolean unlocking) {
		this.unlocking = unlocking;
	}


	public Integer getAtemptCount() {
		return atemptCount;
	}


	public void setAtemptCount(Integer atemptCount) {
		this.atemptCount = atemptCount;
	}


	public LocalDateTime getLastUsed() {
		return lastUsed;
	}


	public void setLastUsed(LocalDateTime lastUsed) {
		this.lastUsed = lastUsed;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public LocalDateTime getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}


	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobile=" + mobile + ", username=" + username + ", password=" + password + ", unlocking="
				+ unlocking + ", atemptCount=" + atemptCount + ", lastUsed=" + lastUsed + ", status=" + status
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", roles=" + roles + "]";
	}


	
	
}
