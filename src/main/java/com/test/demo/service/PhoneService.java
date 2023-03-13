package com.test.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.demo.model.Phone;
import com.test.demo.model.User;
import com.test.demo.repository.PhoneRepository;

@Service
public class PhoneService {

	@Autowired
	PhoneRepository phoneRepository;

	public void save(Phone phone) {
		phoneRepository.save(phone);
	}

	public List<Phone> findAll() {
		return phoneRepository.findAll();
	}

	public Set<Phone> findByUser(User user) {
		return phoneRepository.findByUser(user);
	}

}
