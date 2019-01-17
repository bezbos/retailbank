package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.OLD.BranchOLD;
import com.codecentric.retailbank.repository.SpringData.BranchRepository;
import com.codecentric.retailbank.service.interfaces.IBranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BranchService implements IBranchService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BranchRepository branchRepository;


    public BranchService() {
        super();
    }


    @Override
    public BranchOLD getById(Long id) {
        BranchOLD branch = branchRepository.findById(id).get();
        return branch;
    }

    @Override
    public BranchOLD getByDetails(String details) {
        BranchOLD branch = branchRepository.findByDetails(details);
        return branch;
    }

    @Override
    public List<BranchOLD> getAllBranches() {
        List<BranchOLD> branches = branchRepository.findAll();
        return branches;
    }

    @Override
    public Page<BranchOLD> getAllBranchesByPage(Integer pageIndex, int pageSize) {
        Pageable page = new PageRequest(pageIndex, pageSize);
        Page<BranchOLD> branches = branchRepository.findAll(page);
        return branches;
    }

    @Override
    public BranchOLD addBranch(BranchOLD branch) {
        BranchOLD result = branchRepository.save(branch);
        return result;
    }

    @Override
    public BranchOLD updateBranch(BranchOLD branch) {
        BranchOLD result = branchRepository.save(branch);
        return result;
    }

    @Override
    public void deleteBranch(BranchOLD branch) {
        branchRepository.delete(branch);
    }

    @Override
    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }
}
