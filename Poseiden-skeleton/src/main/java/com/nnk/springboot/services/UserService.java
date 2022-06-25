package com.nnk.springboot.services;

import com.nnk.springboot.domain.Provider;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

	private static final Logger logger = LogManager.getLogger("UserService");

	private final UserRepository userRepository;

	@Autowired
	private final BCryptPasswordEncoder bCryptPasswordEncoder ;

	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * Create user
	 * @param user with the information for saving in database
	 * @return a user who has not yet existed in database
	 */
	public User createUser(User user) {
			User u = new User();
			u.setUsername(user.getUsername());
			u.setFullname(user.getFullname());
			u.setProvider(Provider.LOCAL);
			u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			u.setRole("USER");
			userRepository.save(u);
			return u;

	}

	public boolean isExistUserByUsername( User u) {
		User user = userRepository.findByUsername(u.getUsername());
		if (user != null){
			return true;
		}
		return false;
	}

	public User getUserByUsername(String username)  {
		User user = userRepository.findByUsername(username);
		return user;
	}

	public Boolean isValidPassword(String password) {

		logger.info("Check password if it is valid");

		PasswordValidator validator = new PasswordValidator(Arrays.asList(
				new LengthRule(8, 16),
				new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.Digit, 1),
				new CharacterRule(EnglishCharacterData.Special, 1)));
		RuleResult ruleResult = validator.validate(new PasswordData(password));

		logger.debug("Password result: {}", ruleResult.isValid());
		return ruleResult.isValid();
	}

}
