package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.model.domain.RefAccountType;
import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.model.dto.RefAccountStatusDto;
import com.codecentric.retailbank.model.dto.RefAccountTypeDto;
import com.codecentric.retailbank.model.dto.RefTransactionTypeDto;
import com.codecentric.retailbank.service.RefAccountStatusService;
import com.codecentric.retailbank.service.RefAccountTypeService;
import com.codecentric.retailbank.service.RefTransactionTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/refTypes")
public class RefTypesController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "refTypes";

    @Autowired
    private RefAccountTypeService refAccountTypeService;
    @Autowired
    private RefAccountStatusService refAccountStatusService;
    @Autowired
    private RefTransactionTypeService refTransactionTypeService;


    public RefTypesController() {
        super();
    }


    @RequestMapping(value = {"", "/", "/index", "/list"})
    public String getIndexPage(Model model) {
        List<RefAccountType> refAccountTypes = refAccountTypeService.getAllRefAccountTypes();
        List<RefAccountStatus> refAccountStatuses = refAccountStatusService.getAllRefAccountStatus();
        List<RefTransactionType> refTransactionTypes = refTransactionTypeService.getAllRefTransactionTypes();

        model.addAttribute("refAccountTypes", refAccountTypes);
        model.addAttribute("refAccountStatuses", refAccountStatuses);
        model.addAttribute("refTransactionTypes", refTransactionTypes);
        return CONTROLLER_NAME + "/index";
    }

    @RequestMapping(value = {"/form/{type}", "/form/{type}/{id}"})
    public ModelAndView getFormPage(@PathVariable("type") String type,
                                    @PathVariable("id") Optional<Long> id) {
        switch (type) {
            case "accountType": {
                RefAccountType refAccountType = id.isPresent() ?
                        refAccountTypeService.getById(id.get()) : new RefAccountType(0L);

                RefAccountTypeDto refAccountTypeDto = new RefAccountTypeDto(
                        refAccountType.getId(),
                        refAccountType.getCode(),
                        refAccountType.getDescription(),
                        refAccountType.getIsCheckingType(),
                        refAccountType.getIsSavingsType(),
                        refAccountType.getIsCertificateOfDepositType(),
                        refAccountType.getIsMoneyMarketType(),
                        refAccountType.getIsIndividualRetirementType()
                );

                return new ModelAndView(CONTROLLER_NAME + "/form", "refAccountTypeDto", refAccountTypeDto);
            }
            case "accountStatus": {
                RefAccountStatus refAccountStatus = id.isPresent() ?
                        refAccountStatusService.getById(id.get()) : new RefAccountStatus(0L);

                RefAccountStatusDto refAccountStatusDto = new RefAccountStatusDto(
                        refAccountStatus.getId(),
                        refAccountStatus.getCode(),
                        refAccountStatus.getDescription(),
                        refAccountStatus.getIsActive(),
                        refAccountStatus.getIsClosed()
                );

                return new ModelAndView(CONTROLLER_NAME + "/form", "refAccountStatusDto", refAccountStatusDto);
            }
            case "transactionType": {
                RefTransactionType refTransactionType = id.isPresent() ?
                        refTransactionTypeService.getById(id.get()) : new RefTransactionType(0L);

                RefTransactionTypeDto refTransactionTypeDto = new RefTransactionTypeDto(
                        refTransactionType.getId(),
                        refTransactionType.getCode(),
                        refTransactionType.getDescription(),
                        refTransactionType.getIsDeposit(),
                        refTransactionType.getIsWithdrawal()
                );

                return new ModelAndView(CONTROLLER_NAME + "/form", "refTransactionTypeDto", refTransactionTypeDto);
            }
        }

        return new ModelAndView(CONTROLLER_NAME + "/form");
    }

    @RequestMapping(value = "/formSubmit/accountType", method = RequestMethod.POST)
    public String onRefAccountTypeFormSubmit(@ModelAttribute("refAccountTypeDto") @Valid RefAccountTypeDto dto,
                                             BindingResult result,
                                             Model model,
                                             RedirectAttributes redirectAttributes) {
        // Check if valid
        if (dto == null || result.hasErrors()) {
            model.addAttribute("refAccountTypeDto", dto);
            return CONTROLLER_NAME + "/form";
        }

        // Try adding/updating RefAccountType
        try {
            if (dto.getId() != null && dto.getId() != 0) {
                RefAccountType updatedRefAccountType = refAccountTypeService.getById(dto.getId());
                updatedRefAccountType.setFields(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getIsCheckingType(),
                        dto.getIsSavingsType(),
                        dto.getIsCertificateOfDepositType(),
                        dto.getIsMoneyMarketType(),
                        dto.getIsIndividualRetirementType()
                );
                refAccountTypeService.updateRefAccountType(updatedRefAccountType);

                redirectAttributes.addAttribute("message", "Successfully updated RefAccountType.");
            } else {
                RefAccountType newRefAccountType = new RefAccountType();
                newRefAccountType.setFields(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getIsCheckingType(),
                        dto.getIsSavingsType(),
                        dto.getIsCertificateOfDepositType(),
                        dto.getIsMoneyMarketType(),
                        dto.getIsIndividualRetirementType()
                );
                refAccountTypeService.addRefAccountType(newRefAccountType);

                redirectAttributes.addAttribute("message", "Successfully created RefAccountType.");
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());

            // If we get here something went wrong
            redirectAttributes.asMap().clear();
            model.addAttribute("refAccountTypeDto", dto);
            redirectAttributes.addAttribute("error", ex.getMessage());
            return "redirect:/" + CONTROLLER_NAME + "/form";
        }

        return "redirect:/" + CONTROLLER_NAME + "/list";
    }

    @RequestMapping(value = "/formSubmit/accountStatus", method = RequestMethod.POST)
    public String onRefAccountStatusFormSubmit(@ModelAttribute("refAccountStatusDto") @Valid RefAccountStatusDto dto,
                                               BindingResult result,
                                               Model model,
                                               RedirectAttributes redirectAttributes) {
        // Check if valid
        if (dto == null || result.hasErrors()) {
            model.addAttribute("refAccountStatusDto", dto);
            return CONTROLLER_NAME + "/form";
        }

        // Try adding/updating RefAccountType
        try {
            if (dto.getId() != null && dto.getId() != 0) {
                RefAccountStatus updatedRefAccountStatus = refAccountStatusService.getById(dto.getId());
                updatedRefAccountStatus.setFields(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getIsActiveStatus(),
                        dto.getIsClosedStatus()
                );

                refAccountStatusService.updateRefAccountStatus(updatedRefAccountStatus);

                redirectAttributes.addAttribute("message", "Successfully updated RefAccountStatus.");
            } else {
                RefAccountStatus newRefAccountStatus = new RefAccountStatus();
                newRefAccountStatus.setFields(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getIsActiveStatus(),
                        dto.getIsClosedStatus()
                );
                refAccountStatusService.addRefAccountStatus(newRefAccountStatus);

                redirectAttributes.addAttribute("message", "Successfully created RefAccountStatus.");
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());

            // If we get here something went wrong
            redirectAttributes.asMap().clear();
            model.addAttribute("refAccountStatusDto", dto);
            redirectAttributes.addAttribute("error", ex.getMessage());
            return "redirect:/" + CONTROLLER_NAME + "/form";
        }

        return "redirect:/" + CONTROLLER_NAME + "/list";
    }

    @RequestMapping(value = "/formSubmit/transactionType", method = RequestMethod.POST)
    public String onRefTransactionTypeFormSubmit(@ModelAttribute("refTransactionTypeDto") @Valid RefTransactionTypeDto dto,
                                                 BindingResult result,
                                                 Model model,
                                                 RedirectAttributes redirectAttributes) {
        // Check if valid
        if (dto == null || result.hasErrors()) {
            model.addAttribute("refTransactionTypeDto", dto);
            return CONTROLLER_NAME + "/form";
        }

        // Try adding/updating RefTransactionType
        try {
            if (dto.getId() != null && dto.getId() != 0) {
                RefTransactionType updatedRefTransactionType = refTransactionTypeService.getById(dto.getId());
                updatedRefTransactionType.setFields(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getIsDepositType(),
                        dto.getIsWithdrawalType()
                );
                refTransactionTypeService.updateRefTransactionType(updatedRefTransactionType);

                redirectAttributes.addAttribute("message", "Successfully updated RefTransactionType.");
            } else {
                RefTransactionType newRefTransactionType = new RefTransactionType();
                newRefTransactionType.setFields(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getIsDepositType(),
                        dto.getIsWithdrawalType()
                );
                refTransactionTypeService.addRefTransactionType(newRefTransactionType);

                redirectAttributes.addAttribute("message", "Successfully created RefTransactionType.");
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());

            // If we get here something went wrong
            redirectAttributes.asMap().clear();
            model.addAttribute("refTransactionTypeDto", dto);
            redirectAttributes.addAttribute("error", ex.getMessage());
            return "redirect:/" + CONTROLLER_NAME + "/form";
        }

        return "redirect:/" + CONTROLLER_NAME + "/list";
    }

    @RequestMapping(value = "/delete/{type}/{id}", method = RequestMethod.GET)
    public String onRefAccountTypeDelete(@PathVariable("type") String type,
                                         @PathVariable("id") Long id,
                                         RedirectAttributes redirectAttributes) {
        switch (type) {
            case "accountType": {
                try {
                    refAccountTypeService.deleteRefAccountType(id);

                    redirectAttributes.addAttribute("message", "Successfully deleted RefAccountType.");
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage());

                    // Something went wrong...
                    redirectAttributes.asMap().clear();
                    redirectAttributes.addAttribute("error", ex.getMessage());
                    return "redirect:/" + CONTROLLER_NAME + "/list";
                }

                return "redirect:/" + CONTROLLER_NAME + "/list";
            }
            case "accountStatus": {
                try {
                    refAccountStatusService.deleteRefAccountStatus(id);

                    redirectAttributes.addAttribute("message", "Successfully deleted RefAccountStatus.");
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage());

                    // Something went wrong...
                    redirectAttributes.asMap().clear();
                    redirectAttributes.addAttribute("error", ex.getMessage());
                    return "redirect:/" + CONTROLLER_NAME + "/list";
                }

                return "redirect:/" + CONTROLLER_NAME + "/list";
            }
            case "transactionType": {
                try {
                    refTransactionTypeService.deleteRefTransactionType(id);

                    redirectAttributes.addAttribute("message", "Successfully deleted RefTransactionType.");
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage());

                    // Something went wrong...
                    redirectAttributes.asMap().clear();
                    redirectAttributes.addAttribute("error", ex.getMessage());
                    return "redirect:/" + CONTROLLER_NAME + "/list";
                }

                return "redirect:/" + CONTROLLER_NAME + "/list";
            }
        }

        return "redirect:/" + CONTROLLER_NAME + "/list";
    }
}
