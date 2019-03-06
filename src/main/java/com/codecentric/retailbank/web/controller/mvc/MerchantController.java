package com.codecentric.retailbank.web.controller.mvc;

import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/merchant")
public class MerchantController {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final String CONTROLLER_NAME = "merchant";

    private final MerchantService merchantService;
    //endregion

    //region SERVICES
    @Autowired public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }
    //endregion

    //region INDEX
    @GetMapping({"", "/", "/index"})
    public ModelAndView getIndexPage() {
        List<Merchant> merchants = merchantService.getAllMerchants();

        return new ModelAndView(CONTROLLER_NAME + "/index", "merchants", merchants);
    }
    //endregion

}
