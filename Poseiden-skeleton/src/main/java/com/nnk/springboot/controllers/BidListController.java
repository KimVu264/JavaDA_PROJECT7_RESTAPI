package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.validator.BidListValidator;
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
public class BidListController {

    private static final Logger logger = LogManager.getLogger("BidListController");

    @Autowired
    BidListService bidListService;

    @Autowired
    BidListValidator validator;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        try {
            model.addAttribute("bidLists", bidListService.findAll());
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        try {
            return "bidList/add";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        //check data valid and save to db, after saving return bid list
        try {
            //validate
            validator.validate(bidList, result);

            if (!result.hasErrors()) {
                //checkData
                bidListService.save(bidList);
                model.addAttribute("bidLists", bidListService.findAll());
                return "redirect:/bidList/list";
            }
            return "bidList/add";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get Bid by Id and to model then show to the form
        try {
            BidList bidList = bidListService.findById(id);
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        try {
            //validate
            validator.validate(bidList, result);

            if (result.hasErrors()) {
                return "bidList/update";
            }
            bidList.setBidListId(id);
            bidListService.save(bidList);
            model.addAttribute("bidLists", bidListService.findAll());
            return "redirect:/bidList/list";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // Find Bid by Id and delete the bid, return to Bid list
        try {
            BidList bidList = bidListService.findById(id);
            bidListService.delete(bidList);
            model.addAttribute("bidLists", bidListService.findAll());
            return "redirect:/bidList/list";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "redirect:/bidList/list";
    }
}
