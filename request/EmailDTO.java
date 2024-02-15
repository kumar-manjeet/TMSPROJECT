package com.tms.models.request;

import lombok.Data;

@Data
public class EmailDTO {
	private String to;
    private String subject;
    private String text;

}
