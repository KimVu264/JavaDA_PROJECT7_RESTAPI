package com.nnk.springboot.validator;

import com.nnk.springboot.domain.BidList;
import org.junit.Before;
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
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({BidListValidator.class})
public class BidListValidatorTest {

	@Autowired
	private BidListValidator validator;

	private BidList bidList;

	@Test
	public void testNoError() {
	    bidList  = new BidList("AccountTest","Type Test", 10d);

		Errors errors = new BeanPropertyBindingResult(bidList, "bidList");
		validator.validate(bidList, errors);

		// If errors not expected.
		assertTrue(!errors.hasErrors());
	}

	@Test
	public void testErrorBlank() {
		bidList  = new BidList();

		Errors errors = new BeanPropertyBindingResult(bidList, "bidList");
		validator.validate(bidList, errors);

		// If errors are expected.
		assertTrue(errors.hasErrors());

		for (FieldError error : errors.getFieldErrors()) {
			if(error.getField().equals("account"))
			{
				Assertions.assertEquals("bidList.account.invalid.blank", error.getCode());
			} else if(error.getField().equals("type"))
			{
				Assertions.assertEquals("bidList.type.invalid.blank", error.getCode());
			} else if(error.getField().equals("bidQuantity"))
			{
				Assertions.assertEquals("bidList.bidQuantity.invalid.blank", error.getCode());
			}
		}
	}

	@Test
	public void testBidQuantityNotValidNumber() {
		bidList  = new BidList(1);

		Errors errors = new BeanPropertyBindingResult(bidList, "bidList");
		validator.validate(bidList, errors);

		// If errors are expected.
		assertTrue(errors.hasErrors());

		for (FieldError error : errors.getFieldErrors()) {
			if(error.getField().equals("bidQuantity"))

				Assertions.assertEquals("bidList.bidQuantity.invalid.number", error.getCode());
			}
	}
}
