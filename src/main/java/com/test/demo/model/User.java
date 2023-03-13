package com.test.demo.model;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.test.demo.utils.UserForm;
import com.test.demo.utils.Utils;

import lombok.Data;

@Data
@Entity
public class User {

	public User() {
	}
	public User(UserForm userForm) {
		email = userForm.getEmail();
		password = userForm.getPassword();
		created = new Date();
		active = true;
		token = Utils.generateToken(userForm.getEmail());
	}




	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String email;
	private String password;
	private String token;
	private Boolean active;
	private Date created;
	private Date lastLogin;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	@JsonBackReference
	private Set<Phone> phones;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(active, other.active) && Objects.equals(created, other.created)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(lastLogin, other.lastLogin) && Objects.equals(password, other.password)
				&& Objects.equals(token, other.token);
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, created, email, id, lastLogin, password, token);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", token=" + token + ", active="
				+ active + "]";
	}

}
