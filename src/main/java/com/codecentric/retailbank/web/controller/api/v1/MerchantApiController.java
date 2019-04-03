package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.model.dto.MerchantDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.security.UsersUtil;
import com.codecentric.retailbank.service.MerchantService;
import com.codecentric.retailbank.web.controller.api.v1.helpers.PageableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codecentric.retailbank.web.controller.api.v1.helpers.Constant.PAGE_SIZE;

@RestController
@RequestMapping("/api/v1")
public class MerchantApiController {

    //region FIELDS
    private Logger Logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MerchantService merchantService;
    //endregion


    //region HTTP GET
    @GetMapping({"/merchants", "/merchants/{page}"})
    ResponseEntity<PageableList<MerchantDto>> merchants(@PathVariable("page") Optional<Integer> page,
                                                        @RequestParam("details") Optional<String> details) {

        if (details.isPresent()) {
            List<Merchant> merchants = merchantService.getAllMerchantsByDetails(details.get());
            List<MerchantDto> merchantDtos = new ArrayList<>();
            merchants.forEach(
                    merchant -> merchantDtos.add(new MerchantDto(merchant.getId(), merchant.getDetails()))
            );

            PageableList<MerchantDto> pageableMerchantDtos = new PageableList<>(merchantDtos);

            return merchantDtos.size() == 0
                    //  404 NOT FOUND
                    ? ResponseEntity.notFound().build()
                    //  200 OK
                    : ResponseEntity.ok().location(URI.create("/merchants?details=" + details)).body(pageableMerchantDtos);
        }

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = page.isPresent() ? page.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        ListPage<Merchant> merchants = merchantService.getAllMerchants(pageIndex, PAGE_SIZE);
        List<MerchantDto> merchantDtos = new ArrayList<>();
        merchants.getModels()
                .forEach(
                        merchant -> merchantDtos.add(new MerchantDto(merchant.getId(), merchant.getDetails()))
                );

        PageableList<MerchantDto> pageableMerchantDtos = new PageableList<>(pageIndex, merchantDtos, merchants.getPageCount(), merchants.getModelsCount());

        return pageableMerchantDtos.currentPage == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/merchants/" + pageIndex)).body(pageableMerchantDtos);
    }

    @GetMapping("/merchant/{id}")
    ResponseEntity<MerchantDto> merchantById(@PathVariable("id") Long id) {
        Merchant merchant = merchantService.getById(id);
        MerchantDto merchantDto = merchant == null
                ? null
                : new MerchantDto(merchant.getId(), merchant.getDetails());

        return merchantDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/merchant/" + id)).body(merchantDto);
    }

    @GetMapping("/merchant")
    ResponseEntity<MerchantDto> merchantByDetails(@RequestParam("details") String details) {
        Merchant merchant = merchantService.getByDetails(details);
        MerchantDto merchantDto = merchant == null
                ? null
                : new MerchantDto(merchant.getId(), merchant.getDetails());

        return merchantDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/merchant?details=" + details)).body(merchantDto);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/merchant")
    ResponseEntity<MerchantDto> createMerchant(@RequestBody MerchantDto dto) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Merchant result;
        try {
            result = merchantService.addMerchant(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  201 CREATED
        return ResponseEntity.created(URI.create("/merchant/" + result.getId())).body(result.getDto());
    }
    //endregion

    //region HTTP PUT
    @PutMapping("/merchant")
    ResponseEntity<MerchantDto> updateMerchant(@RequestBody MerchantDto dto) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Merchant result;
        try {
            result = merchantService.updateMerchant(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  200 OK
        return ResponseEntity.ok().location(URI.create("/merchant/" + result.getId())).body(result.getDto());
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping("/merchant/{id}")
    ResponseEntity<MerchantDto> deleteMerchant(@PathVariable("id") Long id) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            merchantService.deleteMerchant(id);
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().build();
        }

        //  204 NO CONTENT
        return ResponseEntity.noContent().build();
    }
    //endregion
}
