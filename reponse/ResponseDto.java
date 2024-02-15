package com.tms.models.reponse;

public class ResponseDto {

	private Integer status;
	private String message;
	private Object data;
	public Integer getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public Object getData() {
		return data;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ResponseDto [status=" + status + ", message=" + message + ", data=" + data + "]";
	}
	
	
	
}
