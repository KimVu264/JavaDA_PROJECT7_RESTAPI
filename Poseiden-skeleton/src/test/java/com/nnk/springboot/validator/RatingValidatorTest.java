package com.nnk.springboot.validator;

import com.nnk.springboot.domain.Rating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import({RatingValidator.class})
public class RatingValidatorTest {

	@Autowired
	private RatingValidator validator;

	private Rating rating;

	@Test
	public void testNoError() {
		rating = new Rating("moodysRating Test", "sandPRating test", "fitchRating test", 10d);

		Errors errors = new BeanPropertyBindingResult(rating, "rating");
		validator.validate(rating, errors);

		// If errors are expected.
		assertTrue(!errors.hasErrors());

		for(FieldError error : errors.getFieldErrors())
		{
			Assertions.assertEquals("moodysRating", error.getCode());
			Assertions.assertEquals("sandPRating", error.getCode());
			Assertions.assertEquals("fitchRating", error.getCode());
		}
	}

	@Test
	public void testErrorBlank() {
		rating = new Rating();

		Errors errors = new BeanPropertyBindingResult(rating, "rating");
		validator.validate(rating, errors);

		// If errors are expected.
		assertTrue(errors.hasErrors());

		for(FieldError error : errors.getFieldErrors())
		{
			if(error.getField().equals("moodysRating"))
			{
				Assertions.assertEquals("rating.moodysRating.invalid.blank", error.getCode());
			}
			else if(error.getField().equals("sandPRating"))
			{
				Assertions.assertEquals("rating.sandPRating.invalid.blank", error.getCode());
			}
			else if(error.getField().equals("fitchRating"))
			{
				Assertions.assertEquals("rating.fitchRating.invalid.blank", error.getCode());
			}
			else if(error.getField().equals("orderNumber"))
			{
				Assertions.assertEquals("rating.orderNumber.invalid.blank", error.getCode());
			}
		}
	}

	@Test
	public void testErrorNumber() {
		rating = new Rating(10);

		Errors errors = new BeanPropertyBindingResult(rating, "rating");
		validator.validate(rating, errors);

		// If errors are expected.
		assertTrue(errors.hasErrors());

		for(FieldError error : errors.getFieldErrors())
		{

			if(error.getField().equals("orderNumber"))
			{
				Assertions.assertEquals("rating.orderNumber.invalid.number", error.getCode());
			}
		}
	}
}
