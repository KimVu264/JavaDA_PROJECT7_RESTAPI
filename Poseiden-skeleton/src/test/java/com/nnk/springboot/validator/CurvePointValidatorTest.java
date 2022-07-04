package com.nnk.springboot.validator;

import com.nnk.springboot.domain.CurvePoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import({CurvePointValidator.class})
public class CurvePointValidatorTest {

	@Autowired
	private CurvePointValidator validator;

	private CurvePoint curvePoint;

	@Test
	public void testNoError() {
		curvePoint  = new CurvePoint(1, 20d, 10d);

		Errors errors = new BeanPropertyBindingResult(curvePoint, "curvePoint");
		validator.validate(curvePoint, errors);

		// If errors not expected.
		assertTrue(!errors.hasErrors());
	}

	@Test
	public void testErrorBlank() {
		curvePoint  = new CurvePoint();

		Errors errors = new BeanPropertyBindingResult(curvePoint, "curvePoint");
		validator.validate(curvePoint, errors);

		// If errors are expected.
		assertTrue(errors.hasErrors());

		for (FieldError error : errors.getFieldErrors()) {
			if(error.getField().equals("curveId"))
			{
				Assertions.assertEquals("curvePoint.curveId.invalid.blank", error.getCode());
			} else if(error.getField().equals("term"))
			{
				Assertions.assertEquals("curvePoint.term.invalid.blank", error.getCode());
			} else if(error.getField().equals("value"))
			{
				Assertions.assertEquals("curvePoint.value.invalid.blank", error.getCode());
			}
		}
	}

/*	@Test
	public void testNotValidNumber() {
		curvePoint  = new CurvePoint(1.2, 2,3);

		Errors errors = new BeanPropertyBindingResult(curvePoint, "curvePoint");
		validator.validate(curvePoint, errors);

		// If errors are expected.
		assertTrue(errors.hasErrors());

		for (FieldError error : errors.getFieldErrors())
		{
			if(error.getField().equals("curveId"))
			{
				Assertions.assertEquals("curvePoint.curveId.invalid.number", error.getCode());
			}
			else if(error.getField().equals("term"))
			{
				Assertions.assertEquals("curvePoint.term.invalid.number", error.getCode());
			}
			else if(error.getField().equals("value"))
			{
				Assertions.assertEquals("curvePoint.value.invalid.number", error.getCode());
			}
		}
	}

 */
}
