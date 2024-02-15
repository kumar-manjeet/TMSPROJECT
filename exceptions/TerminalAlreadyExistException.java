package com.tms.exceptions;

public class TerminalAlreadyExistException extends RuntimeException {
	
	private String msg;
	
	public TerminalAlreadyExistException() {
		
	}
	
	public TerminalAlreadyExistException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
