package com.example.iticket.controller;

import com.example.iticket.export.CsvExport;
import com.example.iticket.export.CsvExportFactory;
import com.example.iticket.export.CsvExportReservations;
import com.example.iticket.model.*;
import com.example.iticket.service.PerformanceService;
import com.example.iticket.service.TicketReservationService;
import com.example.iticket.service.TicketService;
import com.example.iticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.AttributeOverride;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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



    @GetMapping(value = "/buyTicket/{id}")
    public ModelAndView buyTicket(@PathVariable(name = "id") Long pid) {
        ModelAndView modelAndView = new ModelAndView();
        Performance performance = performanceService.findById(pid);
        Ticket ticket = performance.getTicket();
        TicketReservation ticketReservation = new TicketReservation();
        modelAndView.addObject("ticketReservation", ticketReservation);
        modelAndView.addObject("ticket", ticket);
        modelAndView.setViewName("buyTicket");
        return modelAndView;
    }

    @PostMapping(value = "/buyTicket/{id}")
    public ModelAndView buyTicket(@PathVariable(name = "id") Long pid,
                                      @Valid TicketReservation ticketReservation, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Performance performance = performanceService.findById(pid);
        Ticket ticket = performance.getTicket();
        if (ticketReservation.getSeats() > ticket.getSeastLeft()) {
            bindingResult
                    .rejectValue("seats", "error.ticketReservation",
                            "Not enough seats");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("ticketReservation", ticketReservation);
            modelAndView.addObject("ticket", ticket);
            modelAndView.setViewName("buyTicket");
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByUserName(auth.getName());
            ticketReservation.setUser(user);
            ticketService.setTicketInformation(ticketReservation, ticket);
            ticket.setSeastLeft(ticket.getSeastLeft() - ticketReservation.getSeats());
            ticketService.save(ticket);
            ticketReservationService.save(ticketReservation);

            modelAndView.setViewName("redirect:/index");

        }
        return modelAndView;
    }

    @GetMapping(value = "/viewReservations")
    public ModelAndView viewReservations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        ModelAndView modelAndView = new ModelAndView();
        List<TicketReservation> listReservations = ticketReservationService.listAllByUser(user);
        modelAndView.addObject("listReservations", listReservations);
        modelAndView.setViewName("viewReservations");
        return modelAndView;
    }

    @RequestMapping("/cancelReservation/{id}")
    public String cancelReservation(@PathVariable (name="id") Long id) {
        TicketReservation ticketReservation = ticketReservationService.getTicketReservation(id);
        Ticket ticket = ticketReservation.getTicket();
        ticket.setSeastLeft(ticket.getSeastLeft() + ticketReservation.getSeats());
        ticketService.save(ticket);
        ticketReservationService.delete(id);
        return "redirect:/index";
    }

    @GetMapping("/download/{type}")
    public void exportToCSV(@PathVariable (name="type") String type, HttpServletResponse response) throws IOException{
        CsvExport csvExport = CsvExportFactory.getExportType(type);
        List<TicketReservation> ticketReservations = ticketReservationService.listAll();
       // csvExport.setTicketReservationService(ticketReservationService);
        csvExport.writeToResponse(response, ticketReservations);
    }


}
