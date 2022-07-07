package com.nnk.springboot.controller;

import com.nnk.springboot.config.UserDetailsServiceImpl;
import com.nnk.springboot.controllers.SignupController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SignupController.class)
public class SignupControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserDetailsServiceImpl userDetailsService;

	@MockBean
	UserService userService;

	@MockBean
	UserValidator validator;

	String err;
	User userSetup ;
	Errors errors;

	@BeforeEach
	void setup()
	{
		err = "Not Found";
		userSetup = new User();
		userSetup.setId(1);
		userSetup.setFullname("VU Kim");
		userSetup.setUsername("kim");
		userSetup.setPassword("Kim123@");
		//userSetup.setProvider(LOCAL);
		userSetup.setRole("ADMIN");
	}

	@Test
	void getSignUpViewReturnSignupPage() throws Exception
	{
		mockMvc.perform(get("/signup")
						.param("username","kim")
						.param("password","Kim123@")
						.param("fullname","Kim VU")
						.param("model", "user"))
				.andExpect(status().isOk())
				.andExpect(view().name("signup"))
				.andExpect(model().hasNoErrors())
				.andExpect(model().size(1))
				.andExpect(model().attributeExists("user"))
				.andReturn();
	}

}
