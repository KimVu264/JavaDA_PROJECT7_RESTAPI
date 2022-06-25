package com.nnk.springboot.service;

import org.junit.Test;
import org.springframework.security.oauth2.client.userinfo.DelegatingOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomOAuth2ServiceTest {

	@Test
	@SuppressWarnings("unchecked")
	public void loadUserWhenUserServiceCanLoadThenReturnUser() {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> userService1 = mock(OAuth2UserService.class);
		OAuth2UserService<OAuth2UserRequest, OAuth2User> userService2 = mock(OAuth2UserService.class);
		OAuth2UserService<OAuth2UserRequest, OAuth2User> userService3 = mock(OAuth2UserService.class);
		OAuth2User mockUser = mock(OAuth2User.class);
		when(userService3.loadUser(any(OAuth2UserRequest.class))).thenReturn(mockUser);
		DelegatingOAuth2UserService<OAuth2UserRequest, OAuth2User> delegatingUserService =
				new DelegatingOAuth2UserService<>(Arrays.asList(userService1, userService2, userService3));
		OAuth2User loadedUser = delegatingUserService.loadUser(mock(OAuth2UserRequest.class));
		assertThat(loadedUser).isEqualTo(mockUser);

	}

	@Test(expected = IllegalArgumentException.class)
	@SuppressWarnings("unchecked")
	public void loadUserWhenUserRequestIsNullThenThrowIllegalArgumentException() {
		DelegatingOAuth2UserService<OAuth2UserRequest, OAuth2User> delegatingUserService =
				new DelegatingOAuth2UserService<>(
						Arrays.asList(mock(OAuth2UserService.class), mock(OAuth2UserService.class)));
		delegatingUserService.loadUser(null);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void loadUserWhenUserServiceCannotLoadThenReturnNull() {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> userService1 = mock(OAuth2UserService.class);
		OAuth2UserService<OAuth2UserRequest, OAuth2User> userService2 = mock(OAuth2UserService.class);
		OAuth2UserService<OAuth2UserRequest, OAuth2User> userService3 = mock(OAuth2UserService.class);

		DelegatingOAuth2UserService<OAuth2UserRequest, OAuth2User> delegatingUserService =
				new DelegatingOAuth2UserService<>(Arrays.asList(userService1, userService2, userService3));

		OAuth2User loadedUser = delegatingUserService.loadUser(mock(OAuth2UserRequest.class));
		assertThat(loadedUser).isNull();
	}
}
