package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        try {
            model.addAttribute("curvePoints", curvePointService.findAll());
            return "curvePoint/list";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        try {
            return "curvePoint/add";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            //checkData
            curvePointService.save(curvePoint);
            model.addAttribute("curvePoints", curvePointService.findAll());
            return "redirect:/curvePoint/list";
        }
        else {
            logger.error("Curve point is not valid");
            return "curvePoint/add";
        }
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            CurvePoint curvePoint = curvePointService.findById(id);
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        } catch (Exception ex) {
            logger.info("Log error: " + ex.getMessage());
        }
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        if(!result.hasErrors())
        {
            //curvePoint.setId(id);
            curvePointService.save(curvePoint);
            model.addAttribute("curvePoints", curvePointService.findAll());
            logger.info("Curve point is updated !");
            return "redirect:/curvePoint/list";
        }
        else {
            logger.error("Id is not exact, please check again !");
            return "/curvePoint/update/{id}";
        }
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            CurvePoint curvePoint = curvePointService.findById(id);
            curvePointService.delete(curvePoint);
            model.addAttribute("curvePoints", curvePointService.findAll());
            return "redirect:/curvePoint/list";
        } catch (Exception ex) {
            logger.error("Log error: " + ex.getMessage());
        }
        return "redirect:/curvePoint/list";
    }
}
