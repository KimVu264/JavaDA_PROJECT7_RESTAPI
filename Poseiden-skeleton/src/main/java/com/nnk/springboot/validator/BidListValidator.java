package com.nnk.springboot.validator;

import com.nnk.springboot.domain.BidList;
import org.apache.logging.log4j.util.Strings;
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

		if (Strings.isBlank(bidList.getAccount())) {
			errors.rejectValue("account", "bidList.account.invalid.blank");
		}
		if (Strings.isBlank(bidList.getType())) {
			errors.rejectValue("type", "bidList.type.invalid.blank");
		}

		if (bidList.getBidQuantity() == null) {
			errors.rejectValue("bidQuantity", "bidList.bidQuantity.invalid.blank");
		} else if(!Pattern.compile("\\d+").matcher(bidList.getBidQuantity().toString()).find()) {
			errors.rejectValue("bidQuantity", "bidList.bidQuantity.invalid.number");
		}
	}

}
