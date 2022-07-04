package com.nnk.springboot.validator;

import com.nnk.springboot.domain.RuleName;
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
@Import({RuleNameValidator.class})
public class RuleNameValidatorTest {

	@Autowired
	private RuleNameValidator validator;

	private RuleName ruleName;

	@Test
	public void testNoError() {
		ruleName = new RuleName("name test", "description", "json", "template test", "sqlStr", "sqlPart");

		Errors errors = new BeanPropertyBindingResult(ruleName, "ruleName");
		validator.validate(ruleName, errors);

		// If errors are expected.
		assertTrue(!errors.hasErrors());
	}

	@Test
	public void testErrorBlank() {
		ruleName = new RuleName();

		Errors errors = new BeanPropertyBindingResult(ruleName, "ruleName");
		validator.validate(ruleName, errors);

		// If errors are expected.
		assertTrue(errors.hasErrors());

		for(FieldError error : errors.getFieldErrors())
		{
			if(error.getField().equals("name"))
			{
				Assertions.assertEquals("ruleName.name.invalid.blank", error.getCode());
			}
			else if(error.getField().equals("description"))
			{
				Assertions.assertEquals("ruleName.description.invalid.blank", error.getCode());
			}
			else if(error.getField().equals("json"))
			{
				Assertions.assertEquals("ruleName.json.invalid.blank", error.getCode());
			}
			else if(error.getField().equals("template"))
			{
				Assertions.assertEquals("ruleName.template.invalid.blank", error.getCode());
			}
			else if(error.getField().equals("sqlStr"))
			{
				Assertions.assertEquals("ruleName.sqlStr.invalid.blank", error.getCode());
			}
			else if(error.getField().equals("sqlPart"))
			{
				Assertions.assertEquals("ruleName.sqlPart.invalid.blank", error.getCode());
			}
		}
	}
}
