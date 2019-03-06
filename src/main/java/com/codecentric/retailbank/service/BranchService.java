package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.repository.BranchRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.interfaces.IBranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BranchService implements IBranchService {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final BranchRepository branchRepository;
    //endregion

    //region CONSTRUCTOR
    @Autowired public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }
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
        branchRepository.delete(branch);
    }

    @Override public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }
    //endregion
}
