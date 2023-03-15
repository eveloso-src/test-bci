package com.test.demo.utils;

import com.test.demo.model.Phone;

import lombok.Data;

@Data
public class UserForm {

	public UserForm() {
	}


	private Integer id;
	private String name;
	private String email;
	private String password;
	private Phone[] phones;


}
