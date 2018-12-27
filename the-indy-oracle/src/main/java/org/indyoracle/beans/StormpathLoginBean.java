package org.indyoracle.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

/**
 * This bean is meant to hold User data from Stormpath.
 * Contains validation for forms.
 * 
 * @author Guy
 *
 */
public class StormpathLoginBean {
	@Email(message = "Your email is not well-formed.")
	@NotNull(message = "Set your email.")
	private String email;
	
	@NotNull(message = "Set your password.")
	private String password;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
