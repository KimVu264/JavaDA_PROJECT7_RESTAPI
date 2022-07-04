package com.nnk.springboot.validator;

import com.nnk.springboot.domain.Trade;
import org.apache.logging.log4j.util.Strings;
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

		if (Strings.isBlank(trade.getAccount()))  {
			errors.rejectValue("account", "trade.account.invalid.blank");
		}
		if (Strings.isBlank(trade.getType())) {
			errors.rejectValue("type", "trade.type.invalid.blank");
		}
		if (trade.getBuyQuantity() == null) {
			errors.rejectValue("buyQuantity", "trade.buyQuantity.invalid.blank");
		} else if(!Pattern.compile("\\d+").matcher(trade.getBuyQuantity().toString()).find()) {
			errors.rejectValue("buyQuantity", "trade.buyQuantity.invalid.number");
		}
	}
}
