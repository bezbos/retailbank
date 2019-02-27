package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.dto.BranchDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.BranchService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codecentric.retailbank.constants.Constant.PAGE_SIZE;

@RestController
@RequestMapping("/api/v1")
public class BranchApiController {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private BranchService branchService;

    //region HTTP GET
    @GetMapping(value = {"/branches", "/branches/{page}"})
    ResponseEntity<PageableList<BranchDto>> branches(@PathVariable("page") Optional<Integer> page) {

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = page.isPresent() ? page.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        ListPage<Branch> branches = branchService.getAllBranchesByPage(pageIndex, PAGE_SIZE);
        List<BranchDto> branchDtos = new ArrayList<>();
        branches.getModels()
                .forEach(
                        b -> branchDtos.add(
                                new BranchDto(
                                        b.getId(),
                                        b.getAddress().getDto(),
                                        b.getBank().getDto(),
                                        b.getRefBranchType().getDto(),
                                        b.getDetails()
                                )));

        PageableList<BranchDto> pageableBranchDtos = new PageableList<>(pageIndex, branchDtos, branches.getPageCount());

        return pageableBranchDtos.currentPage == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE)
                //  200 OK
                : new ResponseEntity<>(pageableBranchDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/branch/{id}")
    ResponseEntity<BranchDto> branchById(@PathVariable("id") Long id) {
        Branch branch = branchService.getById(id);
        BranchDto branchDto = branch == null
                ? null
                : new BranchDto(
                branch.getId(),
                branch.getAddress().getDto(),
                branch.getBank().getDto(),
                branch.getRefBranchType().getDto(),
                branch.getDetails());

        return branch == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(branchDto, HttpStatus.OK);
    }

    @GetMapping(value = "/branch")
    ResponseEntity<BranchDto> branchByDetails(@RequestParam("details") String details) {
        Branch branch = branchService.getByDetails(details);
        BranchDto branchDto = branch == null
                ? null
                : new BranchDto(
                branch.getId(),
                branch.getAddress().getDto(),
                branch.getBank().getDto(),
                branch.getRefBranchType().getDto(),
                branch.getDetails());

        return branch == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(branchDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP POST
    @PostMapping(value = "/branch")
    ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto clientDto) {
        try {
            branchService.addBranch(clientDto.getDBModel());
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  201 CREATED
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }
    //endregion

    //region HTTP PUT
    @PutMapping(value = "/branch")
    ResponseEntity<BranchDto> updateBranch(@RequestBody BranchDto clientDto) {
        try {
            branchService.updateBranch(clientDto.getDBModel());
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  200 OK
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping(value = "/branch/{id}")
    ResponseEntity<BranchDto> deleteBranch(@PathVariable("id") Long id) {
        try {
            branchService.deleteBranch(id);
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //  204 NO CONTENT
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //endregion
}
