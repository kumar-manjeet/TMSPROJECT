package com.tms.exceptions;

public class TerminalNotRegisteredException extends RuntimeException {
	
	private String msg;
	
	public TerminalNotRegisteredException() {
	}
	
	public TerminalNotRegisteredException(String msg) {
		super(msg);
		this.msg = msg;
	}

}
