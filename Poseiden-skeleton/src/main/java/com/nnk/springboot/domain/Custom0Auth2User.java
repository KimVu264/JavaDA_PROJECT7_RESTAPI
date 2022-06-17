package com.nnk.springboot.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class Custom0Auth2User implements OAuth2User {

	private final OAuth2User oAuth2User;

	public Custom0Auth2User(OAuth2User oAuth2User) {
		this.oAuth2User = oAuth2User;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return oAuth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return oAuth2User.getAuthorities();
	}

	@Override
	public String getName()
	{
		return oAuth2User.getAttribute("name");
	}

	public String getLogin()
	{
		return oAuth2User.<String>getAttribute("login");
	}
}
