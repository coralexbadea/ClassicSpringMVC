package com.example.iticket.model;
import org.hibernate.annotations.Cascade;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull(message = "*Please provide a password")
    private String password;
    @Column(name = "userEnabled")
    private Boolean userEnabled;
    @Column(name = "userName")
    @Size(min = 5, max=100, message = "*Your user name must have at least 5 characters")
    @NotNull(message = "*Please provide a user name")
    private String userName;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="users_roles", joinColumns = @JoinColumn (name="userId"),inverseJoinColumns = @JoinColumn(name="roleId"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "user")
    private Set<TicketReservation> reservations = new HashSet<>();


    public Long getUserId() {
        return userId;
    }

    public Set<TicketReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<TicketReservation> reservations) {
        this.reservations = reservations;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(Boolean userEnabled) {
        this.userEnabled = userEnabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
