package com.nnk.springboot.validator;

import com.nnk.springboot.domain.RuleName;
import org.apache.logging.log4j.util.Strings;
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

		if (Strings.isBlank(ruleName.getName())) {
			errors.rejectValue("name", "ruleName.name.invalid.blank");
		}

		if (Strings.isBlank(ruleName.getDescription()))  {
			errors.rejectValue("description", "ruleName.description.invalid.blank");
		}
		if (Strings.isBlank(ruleName.getJson()))  {
			errors.rejectValue("json", "ruleName.json.invalid.blank");
		}
		if (Strings.isBlank(ruleName.getTemplate())) {
			errors.rejectValue("template", "ruleName.template.invalid.blank");
		}
		if (Strings.isBlank(ruleName.getSqlStr()))  {
			errors.rejectValue("sqlStr", "ruleName.sqlStr.invalid.blank");
		}
		if (Strings.isBlank(ruleName.getSqlPart())){
			errors.rejectValue("sqlPart", "ruleName.sqlPart.invalid.blank");
		}
	}
}
