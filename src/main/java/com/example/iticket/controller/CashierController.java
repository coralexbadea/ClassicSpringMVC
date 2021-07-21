package com.example.iticket.controller;

import com.example.iticket.model.Role;
import com.example.iticket.model.User;
import com.example.iticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
public class CashierController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    public ModelAndView users() {
        ModelAndView modelAndView = new ModelAndView();
        Role role = new Role();
        role.setRoleName("CASHIER");
        role.setRoleId(2L);
        List<User> listUsers = userService.listAllByRole("CASHIER");
        modelAndView.addObject("listUsers", listUsers);
        modelAndView.setViewName("users");
        return modelAndView;

    }

    @GetMapping("/editCashier/{id}")
    public ModelAndView editCashier(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndVie = new ModelAndView("editCashier");
        User user = userService.findById(id);
        modelAndVie.addObject("user", user);
        modelAndVie.addObject("userError", false);
        return modelAndVie;
    }


    @PostMapping(value = "/editCashier/{id}")
    public ModelAndView saveCashier(@PathVariable(name = "id") Long id, @NotNull String newUsername) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findById(id);
        User userExists = userService.findUserByUserName(newUsername);
        if (userExists != null) {
            modelAndView.addObject("user", user);
            modelAndView.addObject("userError", true);
            modelAndView.setViewName("/editCashier");
        } else {
            user.setUserName(newUsername);
            System.out.println(user);
            userService.saveUserWithoutEncription(user);
            modelAndView.setViewName("redirect:/users");

        }

        return modelAndView;

    }

    @RequestMapping("/deleteCashier/{id}")
    public String deleteCashier(@PathVariable (name="id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }

}
