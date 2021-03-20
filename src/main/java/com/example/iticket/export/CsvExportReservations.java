package com.example.iticket.export;

import com.example.iticket.model.TicketReservation;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CsvExportReservations extends CsvExport<TicketReservation> {

    public CsvExportReservations() {
        super();
    }

    public void writeToResponse(HttpServletResponse response, List<TicketReservation> list) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=username_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Reservation ID", "Title", "Artist", "seats"};
        String[] nameMapping = {"ticketReservationId", "ptitle", "pname", "seats"};

        csvWriter.writeHeader(csvHeader);
        for(TicketReservation tr : list){
            csvWriter.write(tr, nameMapping);
        }

        csvWriter.close();

    }
}
