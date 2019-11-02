package io.github.mozzi20.santa.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import io.github.mozzi20.santa.datatransferobjects.CreateEventDTO;
import io.github.mozzi20.santa.validators.CreateEventDTOConstraint.CreateEventDTOConstraintValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy=CreateEventDTOConstraintValidator.class)
public @interface CreateEventDTOConstraint {

	String message() default "Event inte giltigt";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	public static class CreateEventDTOConstraintValidator implements ConstraintValidator<CreateEventDTOConstraint, CreateEventDTO> {

		@Override
		public boolean isValid(CreateEventDTO dto, ConstraintValidatorContext context) {
			if(dto.getStartDate() == null || dto.getEndDate() == null || dto.getWishlistDeadlineDate() == null ) {
				return false;
			}
			
			if(dto.getStartDate().after(dto.getWishlistDeadlineDate())) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("Startdatumet måste vara innan deadline:en").addConstraintViolation();
				return false;
			}
			
			if(dto.getWishlistDeadlineDate().after(dto.getEndDate())) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("Deadline:en måste vara innan slutdatumet").addConstraintViolation();
				return false;
			}
			
			return true;
		}
		
	}
	
}

