package com.example.iticket.controller;

import com.example.iticket.export.CsvExport;
import com.example.iticket.export.CsvExportFactory;
import com.example.iticket.model.Ticket;
import com.example.iticket.model.TicketReservation;
import com.example.iticket.model.User;
import com.example.iticket.service.TicketReservationService;
import com.example.iticket.service.TicketService;
import com.example.iticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Controller
public class ReservationController {
    @Autowired
    TicketReservationService ticketReservationService;
    @Autowired
    UserService userService;
    @Autowired
    TicketService ticketService;


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
    public String cancelReservation(@PathVariable(name="id") Long id) {
        TicketReservation ticketReservation = ticketReservationService.getTicketReservation(id);
        Ticket ticket = ticketReservation.getTicket();
        ticket.setSeastLeft(ticket.getSeastLeft() + ticketReservation.getSeats());
        ticketService.save(ticket);
        ticketReservationService.delete(id);
        return "redirect:/index";
    }

    @GetMapping("/download/{type}")
    public void exportToCSV(@PathVariable (name="type") String type, HttpServletResponse response) throws IOException {
        CsvExport csvExport = CsvExportFactory.getExportType(type);
        List<TicketReservation> ticketReservations = ticketReservationService.listAll();
        csvExport.writeToResponse(response, ticketReservations);
    }
}
