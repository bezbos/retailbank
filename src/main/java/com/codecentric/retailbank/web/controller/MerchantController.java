package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.service.MerchantService;
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

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private String CONTROLLER_NAME = "merchant";
    //endregion

    //region SERVICES
    @Autowired
    private MerchantService merchantService;
    //endregion

    //region INDEX
    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model){
        List<Merchant> merchants = merchantService.getAllMerchants();

        model.addAttribute("merchants", merchants);
        return CONTROLLER_NAME + "/index";
    }
    //endregion

}
