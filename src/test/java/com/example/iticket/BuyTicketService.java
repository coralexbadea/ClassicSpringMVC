package com.example.iticket;

import com.example.iticket.service.PerformanceService;
import com.example.iticket.service.TicketReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testcontainers.shaded.okhttp3.ResponseBody;

@Service
public class BuyTicketService {

    public String validate(int seatsRequested, int seatsLeft){
        if (seatsRequested > seatsLeft) {
            return "Invalid";
        } else return "valid";
    }
}
