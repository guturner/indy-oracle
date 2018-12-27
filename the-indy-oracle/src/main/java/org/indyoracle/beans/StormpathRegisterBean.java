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
public class StormpathRegisterBean {
	@NotNull(message = "Set your first name.")
	private String givenName;
	
	@NotNull(message = "Set your last name.")
	private String surname;
	
	@Email(message = "Your email is not well-formed.")
	@NotNull(message = "Set your email.")
	private String email;
	
	@NotNull(message = "Set your password.")
	private String password;
	
	@NotNull(message = "Confirm your password.")
	private String confirmPassword;
	
	@Email(message = "Approver email is not well-formed.")
	@NotNull(message = "Set your approver's email.")
	private String approverEmail;
	
	@NotNull(message = "Set your approver's unique id.")
	private String approverId;

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getApproverEmail() {
		return approverEmail;
	}

	public void setApproverEmail(String approverEmail) {
		this.approverEmail = approverEmail;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
}
