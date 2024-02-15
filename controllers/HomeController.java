package com.tms.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tms.models.User;
import com.tms.repositories.UserRepository;


@Controller
@RequestMapping("/test")
public class HomeController {
    
    @Autowired
    UserRepository userrepo;
    
    @GetMapping("/test")
    public String hello() {
        System.out.println("test");
        return "test";
    }

    @ResponseBody
    @PostMapping("/saveuser")
    public User saveUser(@RequestBody User user) {
            return userrepo.save(user);
    }
}