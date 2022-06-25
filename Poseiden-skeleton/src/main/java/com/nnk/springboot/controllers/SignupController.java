package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SignupController {

	private static final Logger logger = LogManager.getLogger("SignupController");

	private final UserService userService;

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
		if(result.hasErrors())
		{
			return "signup";
		}
		if(!userService.isExistUserByUsername(user))
		{
			if(userService.isValidPassword(user.getPassword()))
			{
				userService.createUser(user);
				return "redirect:/login";
			}
			else {
				model.addAttribute("error", "Password is not valid");
			}
		} else {
			model.addAttribute("error", "Username is already existed");
			return "signup";
		}
		return "signup";
	}
}
