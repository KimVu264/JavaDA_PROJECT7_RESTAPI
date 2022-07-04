package com.nnk.springboot.service;

import com.nnk.springboot.services.CustomOAuth2UserService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CustomOAuth2ServiceTest {

	private final CustomOAuth2UserService customOAuth2UserService = new CustomOAuth2UserService();

	@Test
	public void loadUserWhenUserRequestIsNullThenThrowIllegalArgumentException()
	{
		assertThatIllegalArgumentException().isThrownBy(() -> this.customOAuth2UserService.loadUser(null));
	}
}
