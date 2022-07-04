package com.nnk.springboot.validator;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		if(Strings.isBlank(user.getUsername()))
		{
			errors.rejectValue("username", "user.username.invalid.blank");
		}
		else
		{
			User userValid = userService.findByUsername(user.getUsername());
			if(userValid != null && userValid.getId() != user.getId())
			{
				errors.rejectValue("username", "user.username.invalid.exist");
			}
		}

		if(Strings.isBlank(user.getFullname()))
		{
			errors.rejectValue("fullname", "user.fullname.invalid.blank");
		}

		if(Strings.isBlank(user.getRole()))
		{
			errors.rejectValue("role", "user.role.invalid.blank");
		}

		isValidPassword(user.getPassword(), errors);
	}

	public void isValidPassword(String pass, Errors errors) {
		System.out.println(pass);
		if(pass.equals(""))
		{
			errors.rejectValue("password", "user.password.invalid.blank");
		}
		if(!Pattern.compile("[0-9]").matcher(pass).find())
		{
			errors.rejectValue("password", "user.password.invalid.number");
		}

		if(!Pattern.compile("[a-z]").matcher(pass).find())
		{
			errors.rejectValue("password", "user.password.invalid.lower");
		}

		if(!Pattern.compile("[A-Z]").matcher(pass).find())
		{
			errors.rejectValue("password", "user.password.invalid.upper");
		}

		if(!Pattern.compile("[!@#&()–[{}]:;',?/*~$^+=<>]").matcher(pass).find())
		{
			errors.rejectValue("password", "user.password.invalid.special");
		}

		if(pass.contains(" "))
		{
			errors.rejectValue("password", "user.password.invalid.whitespace");
		}

		if(!"".equals("") && pass.length() < 8)
		{
			errors.rejectValue("password", "user.password.invalid.length");
		}
	}
}
