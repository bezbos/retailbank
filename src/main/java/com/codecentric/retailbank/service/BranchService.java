package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.repository.JDBC.BranchRepositoryJDBC;
import com.codecentric.retailbank.repository.JDBC.wrappers.ListPage;
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
    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private BranchRepositoryJDBC branchRepositoryJDBC;


    @Override
    public Branch getById(Long id) {
        Branch branch = branchRepositoryJDBC.getSingle(id);
        return branch;
    }

    @Override
    public Branch getByDetails(String details) {
        Branch branch = branchRepositoryJDBC.getSingleByDetails(details);
        return branch;
    }

    @Override
    public List<Branch> getAllBranches() {
        List<Branch> branches = branchRepositoryJDBC.findAll();
        return branches;
    }

    @Override
    public ListPage<Branch> getAllBranchesByPage(Integer pageIndex, int pageSize) {
        ListPage<Branch> branches = branchRepositoryJDBC.findAllRange(pageIndex, pageSize);
        return branches;
    }

    @Override
    public Branch addBranch(Branch branch) {
        Branch result = branchRepositoryJDBC.add(branch);
        return result;
    }

    @Override
    public Branch updateBranch(Branch branch) {
        Branch result = branchRepositoryJDBC.update(branch);
        return result;
    }

    @Override
    public void deleteBranch(Branch branch) {
        branchRepositoryJDBC.delete(branch);
    }

    @Override
    public void deleteBranch(Long id) {
        branchRepositoryJDBC.deleteById(id);
    }
}
