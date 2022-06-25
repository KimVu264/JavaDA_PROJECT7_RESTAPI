package com.nnk.springboot.validator;

import com.nnk.springboot.domain.BidList;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class BidListValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return  BidList.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BidList bidList = ( BidList) target;

		if (bidList.getAccount() == null || bidList.getAccount().length() <= 0) {
			errors.rejectValue("account", "bidList.account.invalid.blank");
		}
		if (bidList.getType() == null || bidList.getType().length() <= 0) {
			errors.rejectValue("type", "bidList.type.invalid.blank");
		}

		if (bidList.getBidQuantity() == null) {
			errors.rejectValue("bidQuantity", "bidList.bidQuantity.invalid.blank");
		}

		if (bidList.getBidQuantity() != null && !Pattern.compile("[0-9]").matcher(bidList.getBidQuantity().toString()).find()) {
			errors.rejectValue("bidQuantity", "bidList.bidQuantity.invalid.number");
		}
	}

}
