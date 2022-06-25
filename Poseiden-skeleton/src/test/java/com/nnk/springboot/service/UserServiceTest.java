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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserServiceTest {

	@MockBean
	UserRepository userRepository;

	@Autowired
	UserService userService;

	User userTest;

	@Test
	void findByUsernameTest()
	{
		String username = "kim";
		Mockito.when(userRepository.findByUsername(username)).thenReturn(userTest);

		assertEquals(userTest,userService.getUserByUsername(username));
	}

	@Test
	void passwordValidatorFalseTest()
	{
		String password = "No valid";
		Boolean expected = false;
		Boolean result ;
		result = userService.isValidPassword(password);

		assertEquals(false, result);
	}

	@Test
	void passwordValidatorTrueTest()
	{
		String password = "Test-12345";
		Boolean expected = true;
		Boolean result ;
		result = userService.isValidPassword(password);

		assertEquals(true, result);
	}

	@Test
	void userValidatorTrueTest()
	{
		String username = "kim";
		boolean result;
		result = userService.isExistUserByUsername(userTest);

		assertEquals(true, result);
	}

	@Test
	void userValidatorFalseTest()
	{
		String username ="kim";
		String expected = "Username already used!";
		boolean result;
		Mockito.when(userRepository.findByUsername(username)).thenReturn(userTest);
		result = userService.isExistUserByUsername(userTest);

		assertEquals(expected, result);
	}

	@Test
	void addTest()
	{
		Mockito.when(userRepository.save(userTest)).thenReturn(userTest);
		userService.createUser(userTest);

		verify(userRepository,Mockito.times(1)).save(userTest);
	}
}
