package com.nnk.springboot.validator;

import com.nnk.springboot.domain.Rating;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RatingValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Rating.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Rating rating = (Rating) target;

		if (rating.getMoodysRating() == null || rating.getMoodysRating().length() <= 0) {
			errors.rejectValue("moodysRating", "rating.moodysRating.invalid.blank");
		}
		if (rating.getSandPRating() == null || rating.getSandPRating().length() <= 0) {
			errors.rejectValue("sandPRating", "rating.sandPRating.invalid.blank");
		}
		if (rating.getFitchRating() == null || rating.getFitchRating().length() <= 0) {
			errors.rejectValue("fitchRating", "rating.fitchRating.invalid.blank");
		}

	}
}
