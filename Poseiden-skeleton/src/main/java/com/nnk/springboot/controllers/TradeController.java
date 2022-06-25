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
    private TradeService tradeService;

    @Autowired
    TradeValidator validator;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        try {
            model.addAttribute("trades", tradeService.findAll());
            return "trade/list";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        try {
            return "trade/add";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        try {
            // Validation
            validator.validate(trade, result);
            if (!result.hasErrors()) {
                //checkData
                tradeService.save(trade);
                model.addAttribute("trades", tradeService.findAll());
                return "redirect:/trade/list";
            }
            return "trade/add";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            Trade trade = tradeService.findById(id);
            model.addAttribute("trade", trade);
            return "trade/update";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        try {
            // Validation
            validator.validate(trade, result);

            if (result.hasErrors()) {
                return "trade/update";
            }
            trade.setTradeId(id);
            tradeService.save(trade);
            model.addAttribute("trades", tradeService.findAll());

            return "redirect:/trade/list";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        try {
            Trade trade = tradeService.findById(id);
            tradeService.delete(trade);
            model.addAttribute("trades", tradeService.findAll());
            return "redirect:/trade/list";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "redirect:/trade/list";
    }
}
