package com.nnk.springboot.validator;

import com.nnk.springboot.domain.RuleName;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RuleNameValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return RuleName.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RuleName ruleName = (RuleName) target;

		if (ruleName.getName() == null || ruleName.getName().length() <= 0) {
			errors.rejectValue("name", "ruleName.name.invalid.blank");
		}
		if (ruleName.getDescription() == null || ruleName.getDescription().length() <= 0) {
			errors.rejectValue("description", "ruleName.description.invalid.blank");
		}
		if (ruleName.getJson() == null || ruleName.getJson().length() <= 0) {
			errors.rejectValue("json", "ruleName.json.invalid.blank");
		}
		if (ruleName.getTemplate() == null || ruleName.getTemplate().length() <= 0) {
			errors.rejectValue("template", "ruleName.template.invalid.blank");
		}

		if (ruleName.getSqlStr() == null || ruleName.getSqlStr().length() <= 0) {
			errors.rejectValue("sqlStr", "ruleName.sqlStr.invalid.blank");
		}
		if (ruleName.getSqlPart() == null || ruleName.getSqlPart().length() <= 0) {
			errors.rejectValue("sqlPart", "ruleName.sqlPart.invalid.blank");
		}

	}
}
