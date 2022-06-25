package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
public class LoginController {

    private static final Logger logger = LogManager.getLogger("LoginController");

    @Autowired
    private UserRepository userRepository;

    private final OAuth2AuthorizedClientService authorizedClientService;

    public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        try {
            mav.setViewName("login");
            return mav;
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return mav;
    }

    @PostMapping("/app-logout")
    public ModelAndView logout(Authentication authentication) {
        ModelAndView mav = new ModelAndView();
        try {
          User userEntity = userRepository.findByUsername(authentication.getName());
            if (userEntity != null) {
                SecurityContextHolder.clearContext();
            }
            mav.setViewName("login");
            return mav;
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return mav;
    }

    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        try {
            mav.addObject("users", userRepository.findAll());
            mav.setViewName("user/list");
            return mav;
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return mav;
    }

    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        try {
            String errorMessage = "You are not authorized for the requested data.";
            mav.addObject("errorMsg", errorMessage);
            mav.setViewName("403");
            return mav;
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return mav;
    }
}
