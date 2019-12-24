package com.cbs.email.service;

import java.io.Serializable;

/**
 * @author 
 *
 */
public class MailUser implements Serializable {

	private static final long serialVersionUID = -3476050035672954978L;

	private String firstName;
	private String lastName;
	private String email;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
