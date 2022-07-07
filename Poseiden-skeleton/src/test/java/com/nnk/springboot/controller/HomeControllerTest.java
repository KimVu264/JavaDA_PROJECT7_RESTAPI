package com.nnk.springboot.controller;

import com.nnk.springboot.config.UserDetailsServiceImpl;
import com.nnk.springboot.controllers.HomeController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

	@MockBean
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	MockMvc mockMvc;

	@Test
	@WithMockUser(username="kim")
	public void homeTest() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("homePage"));
	}

	@Test
	public void HomeTestBeforeLogin() throws Exception
	{
		mockMvc.perform(get("/"))
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	@WithMockUser(username="kim")
	public void HomeTestAfterLogin() throws Exception
	{
		mockMvc.perform(get("/"))
				.andExpect(redirectedUrl(null));
	}

	@Test
	@WithMockUser(username = "kim")
	public void adminHomeTest() throws Exception
	{
		mockMvc.perform(get("/admin/home"))
				.andExpect(redirectedUrl("/bidList/list"));
	}
}
