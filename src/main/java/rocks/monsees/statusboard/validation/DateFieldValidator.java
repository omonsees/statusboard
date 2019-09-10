package rocks.monsees.statusboard.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import rocks.monsees.statusboard.model.Status;

public class DateFieldValidator implements ConstraintValidator<DateFieldConstraint, Status> {

	@Override
	public void initialize(DateFieldConstraint constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Status status, ConstraintValidatorContext context) {

		if (status.getBegin() == null || status.getEnd() == null || status.getBegin().isEqual(status.getEnd())) {
			return true;
		} else if (!status.getBegin().isBefore(status.getEnd())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("begin").addConstraintViolation(); // bind error message to property begin instead
																		// of status entity itself
			return false;
		}
		return true;
	}

}