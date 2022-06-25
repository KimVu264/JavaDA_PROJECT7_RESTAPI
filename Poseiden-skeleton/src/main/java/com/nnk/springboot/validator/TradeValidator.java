package com.nnk.springboot.validator;

import com.nnk.springboot.domain.Trade;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;


@Component
public class TradeValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return Trade.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Trade trade = (Trade) target;

		if (trade.getAccount() == null || trade.getAccount().length() <= 0) {
			errors.rejectValue("account", "trade.account.invalid.blank");
		}
		if (trade.getType() == null || trade.getType().length() <= 0) {
			errors.rejectValue("type", "trade.type.invalid.blank");
		}

		if (trade.getBuyQuantity() == null) {
			errors.rejectValue("buyQuantity", "trade.buyQuantity.invalid.blank");
		}

		if (trade.getBuyQuantity() != null && !Pattern.compile("[0-9]").matcher(trade.getBuyQuantity().toString()).find()) {
			errors.rejectValue("buyQuantity", "trade.buyQuantity.invalid.number");
		}

	}
}
