package com.example.iticket.service;

import com.example.iticket.model.Ticket;
import com.example.iticket.model.TicketReservation;
import com.example.iticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;
    public Ticket save(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public Ticket findById(Long id) {
       return ticketRepository.findById(id).get();
    }


    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public void setTicketInformation(TicketReservation ticketReservation, Ticket ticket) {
        ticketReservation.setTicket(ticket);
        ticketReservation.setPname(ticket.getPerformance().getPname());
        ticketReservation.setPtitle(ticket.getPerformance().getPtitle());
    }
}
