package org.indyoracle.beans;

import org.hibernate.validator.constraints.NotEmpty;
import org.indyoracle.validators.Phone;

/**
 * This bean is meant to hold User data from Stormpath.
 * Contains validation for forms.
 * 
 * @author Guy
 *
 */
public class UserBean {
	private String firstName;
	private String lastName;
	
	@Phone(message = "Phone number must be 10 digits without special characters.")
	private String phoneNumber;
	
	@NotEmpty(message = "Enter a phone carrier.")
	private String phoneCarrier;
	
	private String uniqueId;

	public String getPhoneCarrier() {
		return phoneCarrier;
	}

	public void setPhoneCarrier(String phoneCarrier) {
		this.phoneCarrier = phoneCarrier;
	}

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
}
