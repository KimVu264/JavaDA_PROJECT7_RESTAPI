package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
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

	private static final Logger logger = LogManager.getLogger("UserDetailServiceImplLog");

	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User appUser = this.userRepository.findByUserName(userName);

		if (appUser == null) {
			logger.info("User not found! " + userName);
			throw new UsernameNotFoundException("User " + userName + " was not found in the database");
		}

		logger.info("Found User: " + appUser);

		return new org.springframework.security.core.userdetails.User(
				appUser.getUsername(),
				appUser.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(appUser.getRole())));
	}
}
