package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.repository.BranchRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.interfaces.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService implements IBranchService {

    //region FIELDS
    @Autowired
    private BranchRepository branchRepository;
    //endregion

    //region READ
    @Override public Branch getById(Long id) {
        Branch branch = branchRepository.single(id);
        return branch;
    }

    @Override public Branch getByDetails(String details) {
        Branch branch = branchRepository.singleByDetails(details);
        return branch;
    }

    @Override public List<Branch> getAllBranches() {
        List<Branch> branches = branchRepository.all();
        return branches;
    }

    public List<Branch> getAllBranchesByDetails(String details) {
        List<Branch> branches = branchRepository.allByDetails(details);
        return branches;
    }

    @Override public ListPage<Branch> getAllBranchesByPage(Integer pageIndex, int pageSize) {
        ListPage<Branch> branches = branchRepository.allRange(pageIndex, pageSize);
        return branches;
    }
    //endregion

    //region WRITE
    @Override public Branch addBranch(Branch branch) {
        Branch result = branchRepository.add(branch);
        return result;
    }

    @Override public Branch updateBranch(Branch branch) {
        Branch result = branchRepository.update(branch);
        return result;
    }
    //endregion

    //region DELETE
    @Override public void deleteBranch(Branch branch) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        branchRepository.delete(branch);
    }

    @Override public void deleteBranch(Long id) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        branchRepository.deleteById(id);
    }
    //endregion
}
