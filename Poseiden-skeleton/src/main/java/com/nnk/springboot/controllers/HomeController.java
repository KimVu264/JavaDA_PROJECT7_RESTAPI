package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController
{
	private static final Logger logger = LogManager.getLogger("HomeController");

	@RequestMapping("/")
	public String home(Model model) {
		logger.info("Get homePage");
		return "homePage";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		logger.info("Access home admin");
		return "redirect:/bidList/list";
	}
}
