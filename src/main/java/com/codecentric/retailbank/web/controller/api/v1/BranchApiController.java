package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.dto.BranchDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.security.UsersUtil;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codecentric.retailbank.constants.Constant.PAGE_SIZE;

@RestController
@RequestMapping("/api/v1")
public class BranchApiController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private BranchService branchService;
    //endregion


    //region HTTP GET
    @GetMapping({"/branches", "/branches/{page}"})
    ResponseEntity<?> branches(@PathVariable("page") Optional<Integer> page,
                               @RequestParam("details") Optional<String> details) {

        if (details.isPresent()) {
            List<Branch> branches = branchService.getAllBranchesByDetails(details.get());
            List<BranchDto> branchDtos = new ArrayList<>();
            branches.forEach(branch -> branchDtos.add(
                    new BranchDto(
                            branch.getId(),
                            branch.getAddress().getDto(),
                            branch.getBank().getDto(),
                            branch.getRefBranchType().getDto(),
                            branch.getDetails()
                    )));

            PageableList<BranchDto> pageableBranchDtos = new PageableList<>(0L, branchDtos, 0L, 0L);

            return branchDtos.size() == 0
                    //  404 NOT FOUND
                    ? ResponseEntity.notFound().build()
                    //  200 OK
                    : ResponseEntity.ok().location(URI.create("/branches?details=" + details)).body(pageableBranchDtos);
        }

        // If pageIndex is less than 1 set it to 1.
        int pageIndex = page.isPresent() ? page.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 ?
                0 : pageIndex;

        ListPage<Branch> branches = branchService.getAllBranchesByPage(pageIndex, PAGE_SIZE);
        List<BranchDto> branchDtos = new ArrayList<>();
        branches.getModels()
                .forEach(b -> branchDtos.add(
                        new BranchDto(
                                b.getId(),
                                b.getAddress().getDto(),
                                b.getBank().getDto(),
                                b.getRefBranchType().getDto(),
                                b.getDetails()
                        )));

        PageableList<BranchDto> pageableBranchDtos = new PageableList<>(pageIndex, branchDtos, branches.getPageCount(), branches.getModelsCount());

        return pageableBranchDtos.currentPage == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/branches/" + pageIndex)).body(pageableBranchDtos);
    }

    @GetMapping("/branch/{id}")
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

        return branchDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/branch/" + id)).body(branchDto);
    }

    @GetMapping("/branch")
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

        return branchDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/branch?details=" + details)).body(branchDto);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/branch")
    ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto dto) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Branch createdBranch;
        try {
            createdBranch = branchService.addBranch(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  201 CREATED
        return ResponseEntity.created(URI.create("/branch/" + createdBranch.getId())).body(createdBranch.getDto());
    }
    //endregion

    //region HTTP PUT
    @PutMapping("/branch")
    ResponseEntity<BranchDto> updateBranch(@RequestBody BranchDto dto) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Branch updatedBranch;
        try {
            updatedBranch = branchService.updateBranch(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  200 OK
        return ResponseEntity.ok().location(URI.create("/branch/" + updatedBranch.getId())).body(updatedBranch.getDto());
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping("/branch/{id}")
    ResponseEntity<BranchDto> deleteBranch(@PathVariable("id") Long id) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            branchService.deleteBranch(id);
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
