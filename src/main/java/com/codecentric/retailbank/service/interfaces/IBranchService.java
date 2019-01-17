package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.OLD.BranchOLD;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBranchService {

    // GET
    BranchOLD getById(Long id);

    BranchOLD getByDetails(String details);

    List<BranchOLD> getAllBranches();

    // CREATE
    BranchOLD addBranch(BranchOLD branch);

    // UPDATE
    BranchOLD updateBranch(BranchOLD branch);

    // DELETE
    void deleteBranch(BranchOLD branch);

    void deleteBranch(Long id);

    Page<BranchOLD> getAllBranchesByPage(Integer pageIndex, int pageSize);
}
