package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private static final Logger logger = LogManager.getLogger("LoginController");

    private final OAuth2AuthorizedClientService authorizedClientService;

    public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping(value ="/login")
    public String login()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        logger.info("Go on login page");
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        ModelAndView mav = new ModelAndView();
            String errorMessage = "You are not authorized for the requested data.";
            mav.addObject("errorMsg", errorMessage);
            mav.setViewName("403");
            logger.info("Error access");
            return "403";
    }
}
