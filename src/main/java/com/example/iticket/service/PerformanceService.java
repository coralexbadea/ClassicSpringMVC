package com.example.iticket.service;

import com.example.iticket.model.Performance;
import com.example.iticket.model.Ticket;
import com.example.iticket.model.TicketReservation;
import com.example.iticket.model.User;
import com.example.iticket.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PerformanceService {
    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private TicketService ticketService;
    private Performance getById(Long id){
        return performanceRepository.findById(id).get();
    }

    public List<Performance> listAll(){
        return performanceRepository.findAll();
    }

    public Performance save(Performance perf){
        Ticket perfTicket = new Ticket();

        perfTicket.setPerformance(perf);

        perfTicket.setSeastLeft(perf.getPmaxTickets());

        perf.setTicket(perfTicket);
        return performanceRepository.save(perf);

    }

    public Performance saveEdit(Performance perf){

        return performanceRepository.save(perf);

    }

    public void delete(Long id){

        performanceRepository.deleteById(id);
    }

    public Performance findByPtitle(String ptitle) {
        return performanceRepository.findByPtitle(ptitle);
    }

    public Performance findById(Long pid) {
        return performanceRepository.findById(pid).get();
    }
}
