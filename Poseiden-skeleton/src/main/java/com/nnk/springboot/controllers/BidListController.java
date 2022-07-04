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

        model.addAttribute("bidLists", bidListService.findAll());
        logger.info("Get BidList Page");
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {

        logger.info("BidForm to add new bid");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        //check data valid and save to db, after saving return bid list
        validator.validate(bidList, result);
        if (!result.hasErrors()) {
            bidListService.save(bidList);
            model.addAttribute("bidLists", bidListService.findAll());
            logger.info("Bid is saved !",bidList.getAccount());
            return "redirect:/bidList/list";
            }
        else
        {
            logger.error("Bis is not valid, please check again");
            return "bidList/add";
        }
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get Bid by Id and to model then show to the form
            BidList bidList = bidListService.findById(id);
            model.addAttribute("bidList", bidList);
            logger.info("BidForm for updating");
            return "bidList/update";

    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        //validate
        validator.validate(bidList, result);

        if(!result.hasErrors())
        {
            bidList.setBidListId(id);
            bidListService.save(bidList);
            model.addAttribute("bidLists", bidListService.findAll());
            logger.info("Bid is updated successfully !");
            return "redirect:/bidList/list";
        }
        else
        {
            logger.error("Error update bidList");
            return "bidList/update";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // Find Bid by Id and delete the bid, return to Bid list
        BidList bidList = bidListService.findById(id);
        bidListService.delete(bidList);
        model.addAttribute("bidLists", bidListService.findAll());
        logger.info("Bid is deleted successfully !");
        return "redirect:/bidList/list";
    }
}
