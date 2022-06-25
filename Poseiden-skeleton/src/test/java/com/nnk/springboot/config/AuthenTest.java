package com.nnk.springboot.config;

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

public class AuthenTest {

	@Retention(RetentionPolicy.RUNTIME)
	@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
	public @interface WithMockCustomUser {
		String username() default "kim";
		String password() default "Kim123@";
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

/*
	final class WithUserDetailsSecurityContextFactory
			implements WithSecurityContextFactory<WithUserDetails> {

		private UserDetailsService userDetailsService;

		@Autowired
		public WithUserDetailsSecurityContextFactory(UserDetailsService userDetailsService) {
			this.userDetailsService = userDetailsService;
		}

		public SecurityContext createSecurityContext(WithUserDetails withUser) {
			String username = withUser.value();
			Assert.hasLength(username, "value() must be non-empty String");
			UserDetails principal = userDetailsService.loadUserByUsername(username);
			Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
			return context;
		}
	}

 */
}
