package com.test.demo.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.demo.model.Phone;
import com.test.demo.model.User;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {

	Set<Phone> findByUser(User user);

}
