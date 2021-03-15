package com.example.iticket.controller;

import com.example.iticket.model.Performance;
import com.example.iticket.model.User;
import com.example.iticket.service.PerformanceService;
import com.example.iticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    PerformanceService performanceService;
    @Autowired
    private UserService userService;

//    @RequestMapping("/index")
//    public String viewHomePage(Model model){
//        List<Performance> listPerformances = performanceService.listAll();
//        model.addAttribute("listPerformances", listPerformances);
//        return "inedx";
//    }

    @GetMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        List<Performance> listPerformances = performanceService.listAll();
        modelAndView.addObject("listPerformances", listPerformances);
        modelAndView.setViewName("index");
        return modelAndView;
    }


    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = "/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }


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
        Performance performanceExists = performanceService.findByPtitle(performance.getPtitle());
        if (performanceExists != null) {
            bindingResult
                    .rejectValue("ptitle", "error.performance",
                            "There is already a performance registered with the title provided");
        }
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

    @RequestMapping("/deletePerformance/{sid}")
    public String deletePerformance(@PathVariable (name="sid") Long sid) {
        performanceService.delete(sid);
        return "redirect:/index";
    }


}
