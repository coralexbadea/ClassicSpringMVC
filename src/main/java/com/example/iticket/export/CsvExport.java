package com.example.iticket.export;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public abstract class CsvExport<T> {

    public abstract void writeToResponse(HttpServletResponse response, List<T> list) throws IOException;

}
