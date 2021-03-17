package com.example.iticket.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="ticket_reservation")
public class TicketReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketReservationId;
    @Min(1)
    @NotNull
    private int seats;
    private String ptitle;
    private String pname;


    @OneToOne
    @JoinColumn(name="ticket_id")
    private Ticket ticket;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public Long getTicketReservationId() {
        return ticketReservationId;
    }

    public void setTicketReservationId(Long ticketReservationId) {
        this.ticketReservationId = ticketReservationId;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Ticket getTicket() {
        return ticket;
    }
    public String getPtitle() {
        return ptitle;
    }

    public void setPtitle(String ptitle) {
        this.ptitle = ptitle;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
