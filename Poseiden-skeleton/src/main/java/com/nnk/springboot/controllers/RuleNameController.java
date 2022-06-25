package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.validator.RuleNameValidator;
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
    RuleNameService ruleNameService;

    @Autowired
    RuleNameValidator validator;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        try {
            model.addAttribute("ruleNames", ruleNameService.findAll());
            return "ruleName/list";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
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
        try {
            //validate
            validator.validate(ruleName, result);

            if (!result.hasErrors()) {
                ruleNameService.save(ruleName);
                model.addAttribute("ruleNames", ruleNameService.findAll());
                return "redirect:/ruleName/list";
            }
            return "ruleName/add";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            RuleName ruleName = ruleNameService.findById(id);
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        try {
            //validate
            validator.validate(ruleName, result);

            if (result.hasErrors()) {
                return "ruleName/update";
            }
            ruleName.setId(id);
            ruleNameService.save(ruleName);
            model.addAttribute("ruleNames", ruleNameService.findAll());
            return "redirect:/ruleName/list";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        try {
            RuleName ruleName = ruleNameService.findById(id);
            ruleNameService.delete(ruleName);
            model.addAttribute("ruleNames", ruleNameService.findAll());
            return "redirect:/ruleName/list";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "redirect:/ruleName/list";
    }
}
