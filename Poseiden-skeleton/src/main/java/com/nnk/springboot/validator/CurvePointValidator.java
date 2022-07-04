package com.nnk.springboot.validator;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class CurvePointValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CurvePoint.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CurvePoint curvePoint = (CurvePoint) target;

		if (curvePoint.getCurveId() == null) {
			errors.rejectValue("curveId", "curvePoint.curveId.invalid.blank");
		}

		if (curvePoint.getCurveId() != null && !Pattern.compile("[0-9]").matcher(curvePoint.getCurveId().toString()).find()) {
			errors.rejectValue("curveId", "curvePoint.curveId.invalid.number");
		}

		if (curvePoint.getTerm() == null) {
			errors.rejectValue("term", "curvePoint.term.invalid.blank");
		}

		if (curvePoint.getTerm() != null && !Pattern.compile("[0-9]").matcher(curvePoint.getTerm().toString()).find()) {
			errors.rejectValue("term", "curvePoint.term.invalid.number");
		}

		if (curvePoint.getValue() == null) {
			errors.rejectValue("value", "curvePoint.value.invalid.blank");
		} else if(!Pattern.compile("\\d+").matcher(curvePoint.getValue().toString()).find()) {
			errors.rejectValue("value", "curvePoint.value.invalid.number");
		}
	}
}
