package cq.entity;

import javax.persistence.Entity;

import cq.security.entity.AppUser;

@Entity
public class Customer extends AppUser {
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
