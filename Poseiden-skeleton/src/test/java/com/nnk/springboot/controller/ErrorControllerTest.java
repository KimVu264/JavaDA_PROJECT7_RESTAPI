package com.nnk.springboot.controller;

import com.nnk.springboot.config.UserDetailsServiceImpl;
import com.nnk.springboot.controllers.ErrorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ErrorController.class)
@ExtendWith(SpringExtension.class)
@WithMockUser(username="kim")
public class ErrorControllerTest {

	@MockBean
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getAccessDeniedTest() throws Exception {
		mockMvc.perform(get("/error/403"))
				.andExpect(status().isOk())
				.andExpect(view().name("403"));
	}

}
