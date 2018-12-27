package org.indyoracle.beans;

/**
 * This bean is used during the validatePhoneNumber AJAX call on 'User Profile' screen.
 * 
 * @author Guy
 *
 */
public class ValidateResult {
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
