package com.tms.exceptions;

public class DeviceNotRegisteredException extends RuntimeException {
	
	private String msg;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	public DeviceNotRegisteredException() {
		
	}
	
	public DeviceNotRegisteredException(String msg) {
		this.msg=msg;
		
	}

}
