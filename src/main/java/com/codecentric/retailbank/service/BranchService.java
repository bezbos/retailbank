package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.repository.BankRepository;
import com.codecentric.retailbank.repository.BranchRepository;
import com.codecentric.retailbank.service.interfaces.IBranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BranchService implements IBranchService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BranchRepository repo;


    public BranchService() {
        super();
    }


    @Override
    public Branch getById(Long id) {
        Branch branch = repo.findById(id).get();
        return branch;
    }

    @Override
    public Branch getByDetails(String details) {
        Branch branch = repo.findByDetails(details);
        return branch;
    }

    @Override
    public List<Branch> getAllBranches() {
        List<Branch> branches = repo.findAll();
        return branches;
    }

    @Override
    public Branch addBranch(Branch branch) {
        Branch result = repo.save(branch);
        return result;
    }

    @Override
    public Branch updateBranch(Branch branch) {
        Branch result = repo.save(branch);
        return result;
    }

    @Override
    public void deleteBranch(Branch branch) {
        repo.delete(branch);
    }

    @Override
    public void deleteBranch(Long id) {
        repo.deleteById(id);
    }
}
