package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.UserDoesNotExist;
import com.nnk.springboot.exception.UserExisted;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.regex.Pattern;

public class UserService {

	private static final Logger logger = LogManager.getLogger("UserServiceLog");

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * Create user
	 * @param user with the information for saving in database
	 * @return a user who has not yet existed in database
	 */
	public User createUser(User user) throws UserExisted {
		if (user.getUsername() != null)
		{
			if(isExistUserByUsername(user)) {
				throw new UserExisted();
			}
			User u = new User();
			u.setUsername(user.getUsername());
			u.setFullname(user.getFullname());
			u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			u.setRole(user.getRole());
			userRepository.save(u);

			return u;
		}
		return null;
	}

	public boolean isExistUserByUsername( User u) {
		User user = userRepository.findByUserName(u.getUsername());
		if (user != null){
			return true;
		}
		return false;
	}

	public User getUserByUsername(String username) throws UserDoesNotExist {
		User user = userRepository.findByUserName(username);
		if (user == null) {
			throw new UserDoesNotExist();
		}
		return user;
	}

	public void isValidPassword(String password, Errors errors) {
		logger.info("Check password if it is valid");
		if(password.equals("")){
			errors.rejectValue("password", "user.password.invalid.blank");
		}
		if (!Pattern.compile("[0-9]").matcher(password).find()) {
			errors.rejectValue("password", "user.password.invalid.number");
		}

		if (!Pattern.compile("[a-z]").matcher(password).find()) {
			errors.rejectValue("password", "user.password.invalid.lower");
		}

		if (!Pattern.compile("[A-Z]").matcher(password).find()) {
			errors.rejectValue("password", "user.password.invalid.upper");
		}

		if (!Pattern.compile("[!@#&()–[{}]:;',?/*~$^+=<>]").matcher(password).find()) {
			errors.rejectValue("password", "user.password.invalid.special");
		}

		if (password.contains(" ")) {
			errors.rejectValue("password", "user.password.invalid.whitespace");
		}

		if (!"".equals("") && password.length() < 8) {
			errors.rejectValue("password", "user.password.invalid.length");
		}
	}
}
