package com.test.demo.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
public class Phone {

	public Phone() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private long number;
	private int citycode;
	private String countrycode;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	@JsonManagedReference
	private User user;

	@Override
	public String toString() {
		return "Phone [id=" + id + ", number=" + number + ", citycode=" + citycode + ", countrycode=" + countrycode
				+ ", user=" + user + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phone other = (Phone) obj;
		return citycode == other.citycode && Objects.equals(countrycode, other.countrycode)
				&& Objects.equals(id, other.id) && number == other.number && Objects.equals(user, other.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(number, citycode, countrycode);
	}

}
