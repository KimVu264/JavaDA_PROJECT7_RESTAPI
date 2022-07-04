package com.nnk.springboot.validator;

import com.nnk.springboot.domain.Rating;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class RatingValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Rating.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Rating rating = (Rating) target;

		if (Strings.isBlank(rating.getMoodysRating())) {
			errors.rejectValue("moodysRating", "rating.moodysRating.invalid.blank");
		}
		if (Strings.isBlank(rating.getSandPRating())) {
			errors.rejectValue("sandPRating", "rating.sandPRating.invalid.blank");
		}
		if (Strings.isBlank(rating.getFitchRating())) {
			errors.rejectValue("fitchRating", "rating.fitchRating.invalid.blank");
		}
		if (rating.getOrderNumber() == null) {
			errors.rejectValue("orderNumber", "rating.orderNumber.invalid.blank");
		} else if(!Pattern.compile("\\d+").matcher(rating.getOrderNumber().toString()).find()) {
			errors.rejectValue("orderNumber", "rating.orderNumber.invalid.number");
		}

	}
}
