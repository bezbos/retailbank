package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.repository.JDBC.wrappers.ListPage;

import java.util.List;

public interface IBranchService {

    // GET
    Branch getById(Long id);

    Branch getByDetails(String details);

    List<Branch> getAllBranches();

    // CREATE
    Branch addBranch(Branch branch);

    // UPDATE
    Branch updateBranch(Branch branch);

    // DELETE
    void deleteBranch(Branch branch);

    void deleteBranch(Long id);

    ListPage<Branch> getAllBranchesByPage(Integer pageIndex, int pageSize);
}
