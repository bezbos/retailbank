package com.codecentric.retailbank.web.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping({"/", "/home", "/index", "/home/index"})
    public String getHome() {

        return "index.html";
    }

}
