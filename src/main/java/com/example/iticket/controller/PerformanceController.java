package com.example.iticket.controller;

import com.example.iticket.model.Performance;
import com.example.iticket.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PerformanceController {
    @Autowired
    PerformanceService performanceService;

    @GetMapping(value = "/newPerformance")
    public ModelAndView newPerformance() {
        ModelAndView modelAndView = new ModelAndView();
        Performance performance = new Performance();
        modelAndView.addObject("performance", performance);
        modelAndView.setViewName("newPerformance");
        return modelAndView;
    }

    @PostMapping(value = "/newPerformance")
    public String createNewPerformance(@Valid Performance performance, BindingResult bindingResult) {
        performanceService.save(performance);
        return "redirect:/index";

    }


    @GetMapping("/editPerformance/{pid}")
    public ModelAndView editPerformance(@PathVariable(name = "pid") Long pid) {
        ModelAndView modelAndVie = new ModelAndView("editPerformance");
        Performance performance = performanceService.findById(pid);
        modelAndVie.addObject("performance", performance);
        return modelAndVie;
    }

    @PostMapping(value = "/save")
    public String savePerformance(@ModelAttribute("performance") Performance performance) {
        performanceService.save(performance);
        return "redirect:/index";
    }

    @PostMapping(value = "/saveEdit")
    public String saveEditPerformance(@ModelAttribute("performance") Performance performance) {
        performanceService.saveEdit(performance);
        return "redirect:/index";
    }

    @RequestMapping("/deletePerformance/{sid}")
    public String deletePerformance(@PathVariable (name="sid") Long sid) {
        performanceService.delete(sid);
        return "redirect:/index";
    }







}
