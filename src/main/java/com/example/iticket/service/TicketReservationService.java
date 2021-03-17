package com.example.iticket.service;

import com.example.iticket.model.Ticket;
import com.example.iticket.model.TicketReservation;
import com.example.iticket.model.User;
import com.example.iticket.repository.TicketReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketReservationService {
    @Autowired
    private TicketReservationRepository ticketReservationRepository;
    public void save(TicketReservation ticketReservation) {
        ticketReservationRepository.save(ticketReservation);
    }

    public List<TicketReservation> listAllByUser(User user) {
//        List<TicketReservation> list = new ArrayList<>();
//
//        ticketReservationRepository.findAllByUser(user).forEach(list::add);
//        return list;
        return ticketReservationRepository.findAllByUser(user);
    }

    public List<TicketReservation> listAll() {
        List<TicketReservation> list = new ArrayList<>();

        ticketReservationRepository.findAll().forEach(list::add);
        return list;

    }

    @Transactional
    public void delete(Long id) {

        ticketReservationRepository.deleteById(id);
    }

    public TicketReservation getTicketReservation(Long id){
        return ticketReservationRepository.findById(id).get();
    }
}
