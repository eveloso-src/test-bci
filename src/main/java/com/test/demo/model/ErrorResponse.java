package com.test.demo.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ErrorResponse {

	public ErrorResponse(String message, int code) {
		detail = message;
		this.code = code;
		timestamp = new Timestamp(System.currentTimeMillis());
	}
	private Timestamp timestamp;
	private int code;
	private String detail;

}
