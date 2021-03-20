package com.example.iticket.controller;

import com.example.iticket.export.CsvExport;
import com.example.iticket.export.CsvExportFactory;
import com.example.iticket.model.Performance;
import com.example.iticket.model.Ticket;
import com.example.iticket.model.TicketReservation;
import com.example.iticket.model.User;
import com.example.iticket.service.PerformanceService;
import com.example.iticket.service.TicketReservationService;
import com.example.iticket.service.TicketService;
import com.example.iticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class TicketController {

    @Autowired
    PerformanceService performanceService;
    @Autowired
    UserService userService;
    @Autowired
    TicketService ticketService;
    @Autowired
    TicketReservationService ticketReservationService;
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


}
