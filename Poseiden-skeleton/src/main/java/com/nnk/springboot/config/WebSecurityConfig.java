package com.nnk.springboot.config;

import com.nnk.springboot.services.Custom0Auth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsServiceImpl userDetailsService;

	public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Autowired
	private Custom0Auth2UserService oAuthUserService;


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/login", "/oauth/**").permitAll()
				.antMatchers("/user/*").hasAuthority("ADMIN")
				.anyRequest().authenticated();

		http.authorizeRequests().and()
				.exceptionHandling().accessDeniedPage("/403");

		http.authorizeRequests().and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/homePage")
				.failureUrl("/login?error=true")
				.and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout=true")
				.and()
				.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint()
				.userService(oAuthUserService);

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
