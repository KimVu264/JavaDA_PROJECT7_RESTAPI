package com.nnk.springboot.validator;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import(UserValidator.class)
public class UserValidatorTest {

	@Autowired
	private UserValidator validator;

	@MockBean
	UserService userService;

	private User user;

	@Test
	public void testNoError() {
		user  = new User(1, "Fullname", "username", "Test123@", "USER");

		Errors errors = new BeanPropertyBindingResult(user, "user");
		validator.validate(user, errors);

		// If errors are expected.
		assertTrue(!errors.hasErrors());

	}

	@Test
	public void testErrorBlank() {
		user = new User();
		user.setPassword("Test123@");

		Errors errors = new BeanPropertyBindingResult(user, "user");
		validator.validate(user, errors);

		// If errors are expected.
		assertTrue(errors.hasErrors());

		for(FieldError error : errors.getFieldErrors())
		{
			if(error.getField().equals("fullname"))
			{
				Assertions.assertEquals("user.fullname.invalid.blank", error.getCode());
			}
			else if(error.getField().equals("username"))
			{
				Assertions.assertEquals("user.username.invalid.blank", error.getCode());
			}
			else if(error.getField().equals("role"))
			{
				Assertions.assertEquals("user.role.invalid.blank", error.getCode());
			}

		}
	}
}
