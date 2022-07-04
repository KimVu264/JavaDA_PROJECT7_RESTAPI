package com.nnk.springboot.service;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

	@MockBean
	UserRepository userRepository;

	@Autowired
	UserService userService;

	User user;

	@BeforeEach
	void setupTest()
	{
		user = new User(1, "Kim VU", "kim","Kimkim123@", "ADMIN");
	}

	@Test
	void findByUsernameTest()
	{
		String username = "kim";

		Mockito.when(userRepository.findByUsername(username)).thenReturn(user);

		assertEquals(user,userService.findByUsername(username));
	}

	@Test
	void saveUserTest() {
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userService.save(user));
	}

	@Test
	void findByIdTest()
	{
		Mockito.when(userRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(user));
		assertThat(userService.findById(1)).isEqualTo(user);
	}

	@Test
	void findAllTest()
	{
		List<User> users = userService.findAll();
		Mockito.when(userRepository.findAll()).thenReturn(users);
		assertNotNull(users);
		assertEquals(0, users.size());
	}

	@Test
	public void deleteUserTest() {
		userService.delete(user);

		Mockito.verify(userRepository, Mockito.times(1))
				.delete(user);
	}

	@Test
	void createUserTest()
	{
		Mockito.when(userRepository.save(user)).thenReturn(user);
		userService.createUser(user);

		verify(userRepository,Mockito.times(1)).save(user);
	}
}
