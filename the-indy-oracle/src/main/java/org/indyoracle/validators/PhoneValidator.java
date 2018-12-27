package org.indyoracle.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Custom form validation for Phone Number fields.
 * 
 * @author Guy
 *
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

	@Override
	public void initialize(Phone paramA) {
	}

	@Override
	public boolean isValid(String phoneNo, ConstraintValidatorContext ctx) {
		if (phoneNo == null) {
			return false;
		}

		// Validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{10}")) {
			return true;
		} else {
			return false;
		}
	}

}