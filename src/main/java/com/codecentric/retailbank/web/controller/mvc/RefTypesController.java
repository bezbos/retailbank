package com.codecentric.retailbank.web.controller.mvc;

import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.model.domain.RefAccountType;
import com.codecentric.retailbank.model.domain.RefBranchType;
import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.model.dto.RefAccountStatusDto;
import com.codecentric.retailbank.model.dto.RefAccountTypeDto;
import com.codecentric.retailbank.model.dto.RefBranchTypeDto;
import com.codecentric.retailbank.model.dto.RefTransactionTypeDto;
import com.codecentric.retailbank.service.RefAccountStatusService;
import com.codecentric.retailbank.service.RefAccountTypeService;
import com.codecentric.retailbank.service.RefBranchTypeService;
import com.codecentric.retailbank.service.RefTransactionTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("/refTypes")
public class RefTypesController {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final String CONTROLLER_NAME = "refTypes";

    private final RefBranchTypeService refBranchTypeService;
    private final RefAccountTypeService refAccountTypeService;
    private final RefAccountStatusService refAccountStatusService;
    private final RefTransactionTypeService refTransactionTypeService;
    //endregion

    //region CONTROLLER
    @Autowired
    public RefTypesController(RefBranchTypeService refBranchTypeService,
                              RefAccountTypeService refAccountTypeService,
                              RefAccountStatusService refAccountStatusService,
                              RefTransactionTypeService refTransactionTypeService) {
        this.refBranchTypeService = refBranchTypeService;
        this.refAccountTypeService = refAccountTypeService;
        this.refAccountStatusService = refAccountStatusService;
        this.refTransactionTypeService = refTransactionTypeService;
    }
    //endregion


    //region INDEX
    @GetMapping({"", "/", "/index", "/list"})
    public ModelAndView getIndexPage() {
        return new ModelAndView(CONTROLLER_NAME + "/index", new HashMap<String, Object>() {
            {
                put("refBranchTypes", refBranchTypeService.getAllRefBranchTypes());
                put("refAccountTypes", refAccountTypeService.getAllRefAccountTypes());
                put("refAccountStatuses", refAccountStatusService.getAllRefAccountStatus());
                put("refTransactionTypes", refTransactionTypeService.getAllRefTransactionTypes());
            }
        });
    }
    //endregion

    //region FORM
    @GetMapping({"/form/{type}", "/form/{type}/{id}"})
    public ModelAndView getFormPage(@PathVariable("type") String type,
                                    @PathVariable("id") Optional<Long> id) {
        switch (type) {
            case "branchType": {
                RefBranchType refBranchType = id.isPresent() ?
                        refBranchTypeService.getById(id.get()) : new RefBranchType(0L);

                RefBranchTypeDto refBranchTypeDto = new RefBranchTypeDto(
                        refBranchType.getId(),
                        refBranchType.getCode(),
                        refBranchType.getDescription(),
                        refBranchType.getIsLargeUrban(),
                        refBranchType.getIsSmallRural(),
                        refBranchType.getIsMediumSuburban()
                );

                return new ModelAndView(CONTROLLER_NAME + "/form", "refBranchTypeDto", refBranchTypeDto);
            }
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

    @PostMapping("/formSubmit/branchType")
    public String onRefBranchTypeFormSubmit(@ModelAttribute("refBranchTypeDto") @Valid RefBranchTypeDto dto,
                                            BindingResult result,
                                            Model model,
                                            RedirectAttributes redirectAttributes) {
        // Check if valid
        if (dto == null || result.hasErrors()) {
            model.addAttribute("refBranchTypeDto", dto);
            return CONTROLLER_NAME + "/form";
        }

        // Try adding/updating RefBranchType
        try {
            if (dto.getId() != null && dto.getId() != 0) {
                RefBranchType updatedRefBranchType = refBranchTypeService.getById(dto.getId());
                updatedRefBranchType.setFields(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getIsLargeUrbanType(),
                        dto.getIsSmallRuralType(),
                        dto.getIsMediumSuburbanType()
                );
                refBranchTypeService.updateRefBranchType(updatedRefBranchType);

                redirectAttributes.addAttribute("message", "Successfully updated RefBranchType.");
            } else {
                RefBranchType newRefBranchType = new RefBranchType();
                newRefBranchType.setFields(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getIsLargeUrbanType(),
                        dto.getIsSmallRuralType(),
                        dto.getIsMediumSuburbanType()
                );
                refBranchTypeService.addRefBranchType(newRefBranchType);

                redirectAttributes.addAttribute("message", "Successfully created RefBranchType.");
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());

            // If we get here something went wrong
            redirectAttributes.asMap().clear();
            model.addAttribute("refBranchTypeDto", dto);
            redirectAttributes.addAttribute("error", ex.getMessage());
            return "redirect:/" + CONTROLLER_NAME + "/form";
        }

        return "redirect:/" + CONTROLLER_NAME + "/list";
    }

    @PostMapping("/formSubmit/accountType")
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

    @PostMapping("/formSubmit/accountStatus")
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

    @PostMapping("/formSubmit/transactionType")
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
    //endregion

    //region DELETE
    @GetMapping("/delete/{type}/{id}")
    public String onRefAccountTypeDelete(@PathVariable("type") String type,
                                         @PathVariable("id") Long id,
                                         RedirectAttributes redirectAttributes) {
        switch (type) {
            case "branchType": {
                try {
                    refBranchTypeService.deleteRefBranchType(id);

                    redirectAttributes.addAttribute("message", "Successfully deleted RefBranchType.");
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage());

                    // Something went wrong...
                    redirectAttributes.asMap().clear();
                    redirectAttributes.addAttribute("error", ex.getMessage());
                    return "redirect:/" + CONTROLLER_NAME + "/list";
                }

                return "redirect:/" + CONTROLLER_NAME + "/list";
            }
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
    //endregion

}
