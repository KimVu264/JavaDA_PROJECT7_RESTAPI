package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SignupController {

	private static final Logger logger = LogManager.getLogger("SignupController");

	@Autowired
	UserService userService;

	@Autowired
	UserValidator validator;

	public SignupController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/signup")
	public String signUpView(Model model) {
		model.addAttribute("user", new User());
		logger.info("Go to sign-up page");
		return "signup";
	}

	@PostMapping(value = "/signup")
	public String signUp(@Valid User user, BindingResult result, Model model) {
		try
		{
			//validate
			user.setRole("USER");
			validator.validate(user, result);

			if(result.hasErrors())
			{
				return "signup";
			}
			userService.createUser(user);
			return "redirect:/login";
		}
		catch(Exception ex)
		{
			logger.error("Log error: " + ex.getMessage());
		}
		return "login";
	}
}
