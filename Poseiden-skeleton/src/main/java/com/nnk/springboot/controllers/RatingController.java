package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RatingController {

    private static final Logger logger = LogManager.getLogger("RatingController");

    @Autowired
    RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        try {
            model.addAttribute("ratings", ratingService.findAll());
            return "rating/list";
        } catch (Exception ex) {
            logger.error("Log error: " + ex.getMessage());
        }
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        try {
            return "rating/add";
        } catch (Exception ex) {
            logger.error("Log error: " + ex.getMessage());
        }
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            ratingService.save(rating);
            model.addAttribute("ratings", ratingService.findAll());
            return "redirect:/rating/list";
        }
        else {
            logger.error("Error: rating is not valid " );
            return "rating/add";
        }
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            Rating rating = ratingService.findById(id);
            model.addAttribute("rating", rating);
            return "rating/update";
        } catch (Exception ex) {
            logger.error("Log error: " + ex.getMessage());
        }
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            rating.setId(id);
            ratingService.save(rating);
            model.addAttribute("ratings", ratingService.findAll());
        } else  {
            logger.info("Log error: id is not valid");
        }
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        try {
            Rating rating = ratingService.findById(id);
            ratingService.delete(rating);
            model.addAttribute("ratings", ratingService.findAll());
            return "redirect:/rating/list";
        } catch (Exception ex) {
            logger.error("Log error: " + ex.getMessage());
        }
        return "redirect:/rating/list";
    }
}
