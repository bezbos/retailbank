package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.repository.helpers.ListPage;

import java.util.List;

public interface IBranchService {

    Branch getById(Long id);

    Branch getByDetails(String details);

    List<Branch> getAllBranches();

    Branch addBranch(Branch branch);

    Branch updateBranch(Branch branch);

    void deleteBranch(Branch branch);

    void deleteBranch(Long id);

    ListPage<Branch> getAllBranchesByPage(Integer pageIndex, int pageSize);
}
