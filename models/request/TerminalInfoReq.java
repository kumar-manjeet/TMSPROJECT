package com.tms.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TerminalInfoReq {
	
	private Long terminalId;
	private String version;
	private String ipAddress;
	private String ownerName;
	private String address;
	private String streetAddress;
	private String apartment;
	private String city;
	private String state;
	private double pincode;

}
