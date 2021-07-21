package com.example.iticket.export;

import org.springframework.stereotype.Service;

@Service
public class CsvExportFactory {
    public static CsvExport getExportType(String export){
        if(export.equals("reservations")){
            return new CsvExportReservations();
        }
        return null;
    }
}
