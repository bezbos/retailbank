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

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private BranchRepository branchRepository;


    @Override public Branch getById(Long id) {
        Branch branch = branchRepository.getSingle(id);
        return branch;
    }

    @Override public Branch getByDetails(String details) {
        Branch branch = branchRepository.getSingleByDetails(details);
        return branch;
    }

    @Override public List<Branch> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();
        return branches;
    }

    @Override public ListPage<Branch> getAllBranchesByPage(Integer pageIndex, int pageSize) {
        ListPage<Branch> branches = branchRepository.findAllRange(pageIndex, pageSize);
        return branches;
    }

    @Override public Branch addBranch(Branch branch) {
        Branch result = branchRepository.add(branch);
        return result;
    }

    @Override public Branch updateBranch(Branch branch) {
        Branch result = branchRepository.update(branch);
        return result;
    }

    @Override public void deleteBranch(Branch branch) {
        branchRepository.delete(branch);
    }

    @Override public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }
}
