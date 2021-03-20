package com.example.iticket.model;

import javax.persistence.*;

@Entity
@Table(name="tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @OneToOne
    @JoinColumn(name="performance_id")
    private Performance performance;

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "ticket")
    private TicketReservation ticketReservation;

    private int seastLeft;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public int getSeastLeft() {
        return seastLeft;
    }

    public void setSeastLeft(int seastLeft) {
        this.seastLeft = seastLeft;
    }

    public TicketReservation getTicketReservation() {
        return ticketReservation;
    }

    public void setTicketReservation(TicketReservation ticketReservation) {
        this.ticketReservation = ticketReservation;
    }
}
