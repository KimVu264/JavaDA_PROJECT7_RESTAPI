package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.validator.CurvePointValidator;
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
public class CurveController {

	private static final Logger logger = LogManager.getLogger("CurveController");

	@Autowired
	CurvePointService curvePointService;

	@Autowired
	CurvePointValidator validator;

	@RequestMapping("/curvePoint/list")
	public String home(Model model) {

		model.addAttribute("curvePoints", curvePointService.findAll());
		logger.info("Show CurvePoint List ");
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint bid) {

		logger.info("Show form to add Curve Point ");
		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		//validate
		validator.validate(curvePoint, result);

		if(!result.hasErrors())
		{
			//checkData
			curvePointService.save(curvePoint);
			model.addAttribute("curvePoints", curvePointService.findAll());
			logger.info("Add new curvePoint successfully ! ");
			return "redirect:/curvePoint/list";
		}
		else
		{
			logger.error("CurvePoint has not been saved, please check again !");
			return "curvePoint/add";
		}
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		CurvePoint curvePoint = curvePointService.findById(id);
		model.addAttribute("curvePoint", curvePoint);
		logger.info("Log error: ");
		return "curvePoint/update";
	}

	@PostMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
	                        BindingResult result, Model model) {
		//validate
		validator.validate(curvePoint, result);

		if(!result.hasErrors())
		{
			curvePoint.setId(id);
			curvePointService.save(curvePoint);
			model.addAttribute("curvePoints", curvePointService.findAll());
			logger.info("Update curve point successfully !");
			return "redirect:/curvePoint/list";
		}
		else
		{
			logger.error(" Error update curve point");
			return "curvePoint/update";
		}
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {

		CurvePoint curvePoint = curvePointService.findById(id);
		curvePointService.delete(curvePoint);
		model.addAttribute("curvePoints", curvePointService.findAll());
		logger.info("Delete curvePoint successfully !");
		return "redirect:/curvePoint/list";
	}
}
