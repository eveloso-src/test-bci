package com.test.demo.model;

import lombok.Data;

@Data
public class Response {

	public Response() {
	}


	private ErrorResponse error;
	private Object object;

	public void setObject(Object obj) {
		object = obj;
	}


}
