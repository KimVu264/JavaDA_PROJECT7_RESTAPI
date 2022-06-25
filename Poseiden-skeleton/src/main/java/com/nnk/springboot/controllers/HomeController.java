package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class HomeController
{
	private static final Logger logger = LogManager.getLogger("HomeController");

	@RequestMapping("/")
	public String home(Model model) {
		try {
			return "homePage";
		} catch (Exception ex) {
			logger.info("Log error: " + ex.getMessage());
		}

		return "homePage";
	}

	@RequestMapping("/home")
	public String adminHome(Model model) {
		try {
			return "redirect:/bidList/list";
		} catch (Exception ex) {
			logger.info("Log error: " + ex.getMessage());
		}
		return "redirect:/bidList/list";
	}

}
