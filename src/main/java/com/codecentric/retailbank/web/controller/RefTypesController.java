package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.model.domain.RefAccountType;
import com.codecentric.retailbank.model.domain.RefBranchType;
import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.repository.RefAccountStatusRepository;
import com.codecentric.retailbank.repository.RefAccountTypeRepository;
import com.codecentric.retailbank.repository.RefTransactionTypeRepository;
import com.codecentric.retailbank.service.RefAccountTypeService;
import com.codecentric.retailbank.service.RefBranchTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/refTypes")
public class RefTypesController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "refTypes";

    @Autowired
    private RefBranchTypeService refBranchTypeService;
    @Autowired
    private RefAccountTypeService refAccountTypeService;
    @Autowired
    private RefAccountStatusRepository refAccountStatusRepository;
    @Autowired
    private RefTransactionTypeRepository refTransactionTypeRepository;

    public RefTypesController(){super();}

    @RequestMapping(value = "/testRefBranchType", method = RequestMethod.GET)
    public String getRefBranchTypeTestPage(Model model) {
        boolean readWorks = true;
        boolean addWorks = true;
        boolean updateWorks = true;
        boolean deleteWorks = true;

        try {
            List<RefBranchType> refBranchTypes = refBranchTypeService.getAllRefBranchTypes();
            RefBranchType refBranchType = refBranchTypeService.getById(1L);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            readWorks = false;
        }
        LOGGER.info("LOG: getAllRefBranchTypes() completed successfully.");
        LOGGER.info("LOG: getById(1L) completed successfully.");

        try {
            RefBranchType testRefBranchType = new RefBranchType();
            testRefBranchType.setCode("TEST");

            refBranchTypeService.addRefBranchType(testRefBranchType);
            RefBranchType result = refBranchTypeService.getByCode("TEST");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            addWorks = false;
        }
        LOGGER.info("LOG: addRefBranchType(testRefBranchType) completed successfully.");


        RefBranchType existingRefBranchType = refBranchTypeService.getByCode("TEST");
        try {
            existingRefBranchType.setCode("TEST UPDATED");
            RefBranchType result = refBranchTypeService.updateRefBranchType(existingRefBranchType);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            updateWorks = false;
        }
        LOGGER.info("LOG: updateRefBranchType(existing) completed successfully.");

        try {
            refBranchTypeService.deleteRefBranchType(existingRefBranchType);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            deleteWorks = false;
        }
        LOGGER.info("LOG: deleteRefBranchType(existingRefBranchType) completed successfully.");

        model.addAttribute("readWorks", readWorks);
        model.addAttribute("addWorks", addWorks);
        model.addAttribute("updateWorks", updateWorks);
        model.addAttribute("deleteWorks", deleteWorks);

        return CONTROLLER_NAME + "/testRefBranchType";
    }

    @RequestMapping(value = "/testRefAccountType", method = RequestMethod.GET)
    public String getRefAccountTypeTestPage(Model model) {
        boolean readWorks = true;
        boolean addWorks = true;
        boolean updateWorks = true;
        boolean deleteWorks = true;

        try {
            List<RefAccountType> refAccountTypes = refAccountTypeService.getAllRefAccountTypes();
            RefAccountType refAccountType = refAccountTypeService.getById(1L);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            readWorks = false;
        }
        LOGGER.info("LOG: getAllRefAccountTypes() completed successfully.");
        LOGGER.info("LOG: getById(1L) completed successfully.");

        try {
            RefAccountType testRefAccountType = new RefAccountType();
            testRefAccountType.setCode("TEST");

            refAccountTypeService.addRefAccountType(testRefAccountType);
            RefAccountType result = refAccountTypeService.getByCode("TEST");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            addWorks = false;
        }
        LOGGER.info("LOG: addRefAccountType(testRefAccountType) completed successfully.");


        RefAccountType existingRefAccountType = refAccountTypeService.getByCode("TEST");
        try {
            existingRefAccountType.setCode("TEST UPDATED");
            RefAccountType result = refAccountTypeService.updateRefAccountType(existingRefAccountType);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            updateWorks = false;
        }
        LOGGER.info("LOG: updateRefAccountType(existing) completed successfully.");

        try {
            refAccountTypeService.deleteRefAccountType(existingRefAccountType);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            deleteWorks = false;
        }
        LOGGER.info("LOG: deleteRefAccountType(existingRefAccountType) completed successfully.");

        model.addAttribute("readWorks", readWorks);
        model.addAttribute("addWorks", addWorks);
        model.addAttribute("updateWorks", updateWorks);
        model.addAttribute("deleteWorks", deleteWorks);

        return CONTROLLER_NAME + "/testRefAccountType";
    }


    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model){
        List<RefAccountType> refAccountTypes = refAccountTypeService.getAllRefAccountTypes();
        List<RefAccountStatus> refAccountStatuses = refAccountStatusRepository.findAll();
        List<RefTransactionType> refTransactionTypes = refTransactionTypeRepository.findAll();

        model.addAttribute("refAccountTypes", refAccountTypes);
        model.addAttribute("refAccountStatuses", refAccountStatuses);
        model.addAttribute("refTransactionTypes", refTransactionTypes);
        return CONTROLLER_NAME + "/index";
    }
}
