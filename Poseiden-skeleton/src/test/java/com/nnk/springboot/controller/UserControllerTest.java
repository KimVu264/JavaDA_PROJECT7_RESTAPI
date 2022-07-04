package com.nnk.springboot.controller;

import com.nnk.springboot.config.UserDetailsServiceImpl;
import com.nnk.springboot.controllers.UserController;
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
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ActiveProfiles("test")
public class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserDetailsServiceImpl userDetailsService;

	@MockBean
	UserService userService;

	@MockBean
	UserValidator userValidator;

	User user;
	User newUser;
	List<User> findAll;

	@BeforeEach
	void setupTest()
	{
		findAll = new ArrayList<>(Arrays.asList(new User(1,"Kim VU","kim","Kim123@", "ADMIN"),
				new User(2,"Mimi User","user2","User2hihi@","USER")));
		user = new User(3,"Momo User","user3","User3hihi@","USER");
		newUser = new User(4,"fullNameTest","userTest","Test@123","ADMIN");
	}

	@Test
	void homeTest() throws Exception
	{
		when(userService.findAll()).thenReturn(findAll);

		mockMvc.perform(get("/user/list")
						.with(user("admin")
								.roles("USER","ADMIN")
								.authorities(new SimpleGrantedAuthority("ADMIN"))))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("users"))
				.andExpect(MockMvcResultMatchers.model().attribute("users", findAll))
				.andExpect(view().name("user/list"));
	}

	@Test
	void validateTest() throws Exception
	{
		mockMvc.perform(post("/user/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(String.valueOf(user))
						.with(user("kim")
								.roles("ADMIN")
								.authorities(new SimpleGrantedAuthority("ADMIN")))
						.param("username","userTest")
						.param("password", "Test@123")
						.param("fullname","fullNameTest")
						.param("role","ADMIN"))
						.andExpect(redirectedUrl("/user/list"));
	}

	@Test
	void addUserTest() throws Exception
	{
		mockMvc.perform(get("/user/add")
						.with(user("admin")
								.roles("USER","ADMIN")
								.authorities(new SimpleGrantedAuthority("ADMIN"))))
				.andExpect(status().isOk())
				.andExpect(view().name("user/add"));
	}

	@Test
	void showUpdateFormTest() throws Exception
	{
		when(userService.findById(1)).thenReturn(user);

		mockMvc.perform(get("/user/update/1")
						.with(user("admin")
								.roles("USER","ADMIN")
								.authorities(new SimpleGrantedAuthority("ADMIN"))))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
				.andExpect(MockMvcResultMatchers.model().attribute("user", user))
				.andExpect(view().name("user/update"));
	}

	@Test
	void updateUserTest() throws Exception
	{
		when(userService.save(user)).thenReturn(user);

		mockMvc.perform(post("/user/update/1")
						.with(user("admin")
								.roles("USER","ADMIN")
								.authorities(new SimpleGrantedAuthority("ADMIN")))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("username","username")
						.param("password","user")
						.param("fullname","full name")
						.param("role","ADMIN")
						.with(csrf()))
				.andExpect(redirectedUrl("/user/list"));
	}

	@Test
	void deleteUserTest() throws Exception
	{
		when(userService.findById(1)).thenReturn(user);
		when(userService.findAll()).thenReturn(findAll);
		Mockito.doNothing().when(userService).delete(user);

		mockMvc.perform(get("/user/delete/1")
						.with(user("admin")
								.roles("USER","ADMIN")
								.authorities(new SimpleGrantedAuthority("ADMIN"))))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/user/list"));
	}
}
