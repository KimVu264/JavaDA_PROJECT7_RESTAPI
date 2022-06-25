package com.nnk.springboot.config;

import com.nnk.springboot.domain.CustomOAuth2User;
import com.nnk.springboot.services.CustomOAuth2UserService;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private CustomOAuth2UserService oAuth2UserService = new CustomOAuth2UserService();

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/login", "/app-logout", "/signup").permitAll()
				.antMatchers("/user/**").hasAnyAuthority("ADMIN")
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login").permitAll()
				.defaultSuccessUrl("/",true).permitAll()
				.and().logout().permitAll()
				.and().exceptionHandling().accessDeniedPage("/error/403")
				.and()
				.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint()
				.userService(oAuth2UserService)
				.and()
				.successHandler(new AuthenticationSuccessHandler() {

					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					                                    Authentication authentication) throws IOException, ServletException {

						CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

						userDetailsService.processOAuthPostLogin(oauthUser.getEmail());

						response.sendRedirect("/");
					}
				});
	}
}
