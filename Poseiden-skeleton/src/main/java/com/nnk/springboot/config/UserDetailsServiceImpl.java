package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.Generated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = LogManager.getLogger("UserDetailServiceImpl");

	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Generated
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User appUser = this.userRepository.findByUsername(username);

		if (appUser == null) {
			logger.info("User not found! " + username);
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}

		logger.info("Found User: " + appUser);

		return new org.springframework.security.core.userdetails.User(
				appUser.getUsername(),
				appUser.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(appUser.getRole())));
	}

}
