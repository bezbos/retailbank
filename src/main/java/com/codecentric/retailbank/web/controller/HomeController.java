package com.codecentric.retailbank.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

    // GET: index
    @GetMapping(value = {"/", "/home", "/index", "/home/index"})
    public String getHome(Model model) {
        setModelUsernameAttribute(model, getPrincipalClassName(), getPrincipal());

        return "home/index";
    }
}
