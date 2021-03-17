package com.example.iticket.export;

import com.example.iticket.model.TicketReservation;
import com.example.iticket.service.TicketReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public abstract class CsvExport<T> {

    public abstract void writeToResponse(HttpServletResponse response, List<T> list) throws IOException;

}
