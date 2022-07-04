package com.nnk.springboot.config;

import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.lang.annotation.*;
import java.util.Collections;

@UtilityClass
public class AuthenTest {

	@Retention(RetentionPolicy.RUNTIME)
	@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
	public @interface WithMockCustomUser {
		String username() default "kim";
		String password() default "Kimtest123@";
	}

	public class WithMockCustomUserSecurityContextFactory
			implements WithSecurityContextFactory<WithMockCustomUser> {
		@Override
		public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
			SecurityContext context = SecurityContextHolder.createEmptyContext();

			UserDetails principal =
					new User(customUser.username(), customUser.password(),
							Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
			context.setAuthentication(new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities()));
			return context;
		}
	}
}
