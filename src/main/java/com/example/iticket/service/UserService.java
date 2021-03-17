package com.example.iticket.service;

import com.example.iticket.model.Role;
import com.example.iticket.model.TicketReservation;
import com.example.iticket.model.User;
import com.example.iticket.repository.RoleRepository;
import com.example.iticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserEnabled(true);
        Role userRole = roleRepository.findByRoleName("CASHIER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    public void addReservation(User user, TicketReservation ticketReservation) {
        Set<TicketReservation> res = user.getReservations();
        res.add(ticketReservation);
        user.setReservations(res);
    }
}
