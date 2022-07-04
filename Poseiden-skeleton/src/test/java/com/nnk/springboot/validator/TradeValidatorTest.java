package com.nnk.springboot.validator;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
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

import javax.validation.constraints.AssertTrue;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import(TradeValidator.class)
public class TradeValidatorTest {

	@Autowired
	private TradeValidator validator;

	private Trade trade;

	@Test
	public void testNoError() {
		trade  = new Trade("account", "type", 10d);

		Errors errors = new BeanPropertyBindingResult(trade, "curvePoint");
		validator.validate(trade, errors);

		// If errors are expected.
		assertTrue(!errors.hasErrors());

	}

	@Test
	public void testErrorBlank() {
		trade = new Trade();

		Errors errors = new BeanPropertyBindingResult(trade, "trade");
		validator.validate(trade, errors);

		// If errors are expected.
		assertTrue(errors.hasErrors());

		for(FieldError error : errors.getFieldErrors())
		{
			if(error.getField().equals("account"))
			{
				Assertions.assertEquals("trade.account.invalid.blank", error.getCode());
			}
			else if(error.getField().equals("type"))
			{
				Assertions.assertEquals("trade.type.invalid.blank", error.getCode());
			}
			else if(error.getField().equals("buyQuantity"))
			{
				Assertions.assertEquals("trade.buyQuantity.invalid.blank", error.getCode());
			}
		}
	}
	@Test
	public void testBuyQuantityNotValidNumber() {
		trade  = new Trade(1);

		Errors errors = new BeanPropertyBindingResult(trade, "trade");
		validator.validate(trade, errors);

		// If errors are expected.
		assertTrue(errors.hasErrors());

		for (FieldError error : errors.getFieldErrors()) {
			if(error.getField().equals("buyQuantity"))

				Assertions.assertEquals("trade.buyQuantity.invalid.number", error.getCode());
		}
	}
}
