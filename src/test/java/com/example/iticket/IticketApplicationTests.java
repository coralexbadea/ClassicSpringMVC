package com.example.iticket;
import com.example.iticket.model.Performance;
import com.example.iticket.model.Ticket;
import com.example.iticket.model.TicketReservation;
import com.example.iticket.model.User;
import com.example.iticket.service.PerformanceService;
import com.example.iticket.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.xml.ws.Response;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.plugin2.util.PojoUtil.toJson;

@RunWith(SpringRunner.class)
@SpringBootTest
class IticketApplicationTests {

    @Autowired
    private UserService userService;


    @Test
    void passwordHashing() {
        User user = new User();
        user.setUserName("test123");
        user.setPassword("test");
        User userdb = userService.saveUser(user);
        System.out.println(userdb.getPassword());
        assert ("$2a$10$".equals(userdb.getPassword().substring(0, 7)));
        userService.delete(userdb.getUserId());
    }


//    @Autowired MockMvc mvc;
//    @MockBean BuyTicketService buyTicketService;
//
//
//    ObjectMapper objectMapper = new ObjectMapper();
//
//
//
//    @Test
//    public void buyTicket() throws Exception {
//        Performance performance = new Performance();
//        performance.setPmaxTickets(12);
//        performance.setPdate("12.04.2021");
//        performance.setPgenre("Trace");
//        performance.setPname("Armin");
//        performance.setPtitle("State of Trace");
//        Performance performancedb = performanceService.save(performance);
//        Long id = performancedb.getPid();
//        TicketReservation ticketReservation = new TicketReservation();
//        ticketReservation.setSeats(14);
//
//
//
//        //   String JsonRequest = objectMapper.writeValueAsString(ticketReservation);
//        String url = "/buyTicketTest/"+id;
//        mvc.perform(post(url).content(toJson(ticketReservation)).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        performanceService.delete(id);
//
//    }

}