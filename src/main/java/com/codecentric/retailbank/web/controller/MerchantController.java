package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.repository.MerchantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/merchant")
public class MerchantController {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "merchant";

    @Autowired
    private MerchantRepository merchantRepository;


    public MerchantController(){super();}


    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model){
        List<Merchant> merchants = merchantRepository.findAll();

        model.addAttribute("merchants", merchants);
        return CONTROLLER_NAME + "/index";
    }
}
