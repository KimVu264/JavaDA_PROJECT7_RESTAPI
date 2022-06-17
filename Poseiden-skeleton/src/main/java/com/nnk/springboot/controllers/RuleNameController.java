package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {

    private static final Logger logger = LogManager.getLogger("RuleNameController");

    @Autowired
    RuleNameService service;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        try {
            model.addAttribute("ruleNames", service.findAll());
            return "ruleName/list";
        } catch (Exception ex) {
            logger.error("Log error: " + ex.getMessage());
        }
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        try {
            return "ruleName/add";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            service.save(ruleName);
            model.addAttribute("ruleNames", service.findAll());
            return "redirect:/ruleName/list";
        }
        else {
            logger.error("Log error: Rule name is not valid ");
            return "ruleName/add";
        }
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            RuleName ruleName = service.findById(id);
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        } catch (Exception ex) {
            logger.error("Log error: " + ex.getMessage());
        }
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            ruleName.setId(id);
            service.save(ruleName);
            model.addAttribute("ruleNames", service.findAll());
        } else {
            logger.error("Log error: id is not valid");
        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        try {
            RuleName ruleName = service.findById(id);
            service.delete(ruleName);
            model.addAttribute("ruleNames", service.findAll());
            return "redirect:/ruleName/list";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "redirect:/ruleName/list";
    }
}
