package com.example.iticket.controller;

import com.example.iticket.export.CsvExportFactory;
import com.example.iticket.model.*;
import com.example.iticket.service.PerformanceService;
import com.example.iticket.service.TicketReservationService;
import com.example.iticket.service.TicketService;
import com.example.iticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketReservationService ticketReservationService;
    @Autowired
    private CsvExportFactory csvExportFactory;

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


}
