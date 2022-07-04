package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.validator.TradeValidator;
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
public class TradeController {

	private static final Logger logger = LogManager.getLogger("TradeController");

	@Autowired
	TradeValidator validator;

	@Autowired
	private TradeService tradeService;

	@RequestMapping("/trade/list")
	public String home(Model model) {
		model.addAttribute("trades", tradeService.findAll());
		logger.info("Show trade list");
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addTradeForm(Trade trade) {
		logger.info("Show form to add new trade");
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {

		// Validation
		validator.validate(trade, result);
		if(!result.hasErrors())
		{
			//checkData
			tradeService.save(trade);
			model.addAttribute("trades", tradeService.findAll());
			logger.info("Add new trade successfully !");
			return "redirect:/trade/list";
		}
		else
		{
			logger.error("Error add new trade !");
			return "trade/add";
		}

	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Trade trade = tradeService.findById(id);
		model.addAttribute("trade", trade);
		logger.info("Show form to update trade");
		return "trade/update";

	}

	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
	                          BindingResult result, Model model) {
		// Validation
		validator.validate(trade, result);

		if(!result.hasErrors())
		{
			trade.setTradeId(id);
			tradeService.save(trade);
			model.addAttribute("trades", tradeService.findAll());
			logger.info("Update trade successfully !");
			return "redirect:/trade/list";
		}
		else
		{
			logger.error("Error update trade");
			return "trade/update";
		}
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {

		Trade trade = tradeService.findById(id);
		tradeService.delete(trade);
		model.addAttribute("trades", tradeService.findAll());
		logger.info("Delete trade successfully !");
		return "redirect:/trade/list";


	}
}
