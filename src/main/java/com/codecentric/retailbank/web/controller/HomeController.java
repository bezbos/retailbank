package com.codecentric.retailbank.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {

    // GET: index
    @GetMapping(value = {"/", "/home", "/index", "/home/index"})
    public String getHome(Model model, Principal principal) {
        if (principal != null)
            model.addAttribute("username", principal.getName());

        return "home/index";
    }
}
