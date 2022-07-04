package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.validator.RatingValidator;
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

	@Autowired
	RatingValidator validator;

	@RequestMapping("/rating/list")
	public String home(Model model) {

		model.addAttribute("ratings", ratingService.findAll());
		logger.info("Show rating list ");
		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String addRatingForm(Rating rating) {

		logger.info("Show rating form to add");
		return "rating/add";
	}

	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {

		//validate
		validator.validate(rating, result);

		if(!result.hasErrors())
		{
			ratingService.save(rating);
			model.addAttribute("ratings", ratingService.findAll());
			logger.info("Add new rating successfully !");
			return "redirect:/rating/list";
		}
		else
		{
			logger.info("Error add new rating");
			return "rating/add";
		}
	}

	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		Rating rating = ratingService.findById(id);
		model.addAttribute("rating", rating);
		logger.info("Show form to update rating");
		return "rating/update";
	}

	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
	                           BindingResult result, Model model) {
		//validate
		validator.validate(rating, result);

		if(!result.hasErrors())
		{
			rating.setId(id);
			ratingService.save(rating);
			model.addAttribute("ratings", ratingService.findAll());
			logger.info("Rating is updated successfully !");
			return "redirect:/rating/list";
		}
		else
		{
			logger.error("Error update bidList");
			return "rating/update";
		}
	}

	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {

		Rating rating = ratingService.findById(id);
		ratingService.delete(rating);
		model.addAttribute("ratings", ratingService.findAll());
		logger.info("Delete rating successfully !");
		return "redirect:/rating/list";
	}
}
