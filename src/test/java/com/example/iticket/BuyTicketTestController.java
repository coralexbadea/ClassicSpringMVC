package com.example.iticket;

import com.example.iticket.model.Performance;
import com.example.iticket.model.Ticket;
import com.example.iticket.model.TicketReservation;
import com.example.iticket.service.PerformanceService;
import com.example.iticket.service.TicketReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BuyTicketTestController {

    @Autowired
    PerformanceService performanceService;
    @Autowired
    TicketReservationService ticketReservationService;
    @Autowired
    BuyTicketService buyTicket;
    @RequestMapping(value = "/buyTicketTest/{id}")
    public String buyTicket(@PathVariable(name = "id") Long pid,
                            @RequestBody TicketReservation ticketReservation) {

        Performance performance = performanceService.findById(pid);
        Ticket ticket = performance.getTicket();
        return buyTicket.validate(ticketReservation.getSeats(), ticket.getSeastLeft());
    }
}
