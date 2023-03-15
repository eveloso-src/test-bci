package com.test.demo.service;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.demo.model.ErrorResponse;
import com.test.demo.model.Phone;
import com.test.demo.model.Response;
import com.test.demo.model.User;
import com.test.demo.repository.UserRepository;
import com.test.demo.utils.UserForm;
import com.test.demo.utils.Utils;

@Service
public class UserService {

	private static final int PASS_MAX_LEN = 12;
	private static final int PASS_MIN_LEN = 8;
	private static final int ERROR_EMAIL = 1;
	private static final int ERROR_PASSWORD = 2;
	private static final int ERROR_USER = 3;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PhoneService phoneService;

	public boolean emailValid(String email) {
		if (email == null)
			return false;
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public Response signup(UserForm userForm) {
		Response response = new Response();
		if (!emailValid(userForm.getEmail())) {
			response.setError(new ErrorResponse("Invalid email", ERROR_EMAIL));
		} else if (!passwordValid(userForm.getPassword())) {
			response.setError(new ErrorResponse("Invalid password", ERROR_PASSWORD));
		} else if (null != userRepository.findByEmail(userForm.getEmail())) {
			response.setError(new ErrorResponse("User exists", ERROR_USER));
		} else {
			String passEncode = Base64.getEncoder().encodeToString(userForm.getPassword().getBytes());
			userForm.setPassword(passEncode);
			User user = new User(userForm);
			Set<Phone> phonesSet = new HashSet<>();
			for (Phone phone : userForm.getPhones()) {
				phonesSet.add(phone);
			}
			user.setToken(Utils.generateToken(userForm.getEmail()));
			user.setPhones(phonesSet);
			User newUser = userRepository.save(user);
			if (userForm.getPhones() != null) {

				for (Phone phone : userForm.getPhones()) {
					phone.setUser(newUser);
					phoneService.save(phone);
				}
			}
			response.setObject(newUser);
		}

		return response;
	}

	public boolean passwordValid(String password) {
		if (password == null)
			return false;
		int capital = 0;
		int numbers = 0;
		int i = 0;
		for (; i < password.length() && capital < 2 && numbers < 3; i++) {
			if (Character.isUpperCase(password.charAt(i))) {
				capital++;
			}
			if (!Character.isDigit(password.charAt(i))) {
				numbers++;
			}
		}
		if (capital > 1)
			return false;

		if (password.length() < PASS_MIN_LEN && password.length() > PASS_MAX_LEN) {
			return false;
		}

		if (numbers > 2)
			return false;
		return true;
	}

	public User login(String token, String email) {
		User user = userRepository.findByToken(token);
		user.setToken(Utils.generateToken(email));
		userRepository.save(user);
		return user;
	}

}
