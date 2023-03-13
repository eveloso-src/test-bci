package com.test.demo.controller;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.model.Phone;
import com.test.demo.model.Response;
import com.test.demo.model.User;
import com.test.demo.service.PhoneService;
import com.test.demo.service.UserService;
import com.test.demo.utils.UserForm;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	PhoneService phoneService;

	@PostMapping("/sign-up")
	@Consumes({ javax.ws.rs.core.MediaType.APPLICATION_JSON })
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public ResponseEntity<Response> signup(@RequestBody UserForm user) {
		Response response = userService.signup(user);
		if (response.getError() != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/login")
	@Consumes({ javax.ws.rs.core.MediaType.APPLICATION_JSON })
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public ResponseEntity<Response> login(@RequestParam String token, @RequestParam String email) {
		User user = userService.login(token, email);
		Response response = new Response();
		if (user != null) {
			Set<Phone> listPhones = phoneService.findByUser(user);
			user.setPhones(listPhones);
			response.setObject(user);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

}
