package com.test.demo.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import io.jsonwebtoken.Jwts;

public class Utils {

	public static String generateToken(String email) {
		Instant now = Instant.now();
		String jwtToken = Jwts.builder().claim("email", email).setSubject("user").setId(UUID.randomUUID().toString())
				.setIssuedAt(new Date())
				.setExpiration(Date.from(now.plus(60, ChronoUnit.MINUTES))).compact();
		return jwtToken;
	}

}
