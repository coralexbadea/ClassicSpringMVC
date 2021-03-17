package com.example.iticket.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "performances")
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @NotNull(message = "*Please provide a performer name")
    private String pname;

    @NotNull(message = "*Please provide a genre")
    private String pgenre;

    @Size(min = 5, max=100, message = "*Title must have at least 5 characters")
    @NotNull(message = "*Please provide a Title")
    private String ptitle;

    @NotNull(message = "*Please provide a date")
    private String pdate;

    @NotNull(message = "*Please provide the maximum number of tickets")
    private int pmaxTickets;


    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "performance")
    private Ticket ticket;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPgenre() {
        return pgenre;
    }

    public void setPgenre(String pgenre) {
        this.pgenre = pgenre;
    }

    public String getPtitle() {
        return ptitle;
    }

    public void setPtitle(String ptitle) {
        this.ptitle = ptitle;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public int getPmaxTickets() {
        return pmaxTickets;
    }

    public void setPmaxTickets(int pmaxTickets) {
        this.pmaxTickets = pmaxTickets;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
