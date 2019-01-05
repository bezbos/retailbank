package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefBranchType;
import com.codecentric.retailbank.repository.RefBranchTypeRepository;
import com.codecentric.retailbank.service.interfaces.IRefBranchTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RefBranchTypeService implements IRefBranchTypeService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefBranchTypeRepository repo;


    public RefBranchTypeService() {
        super();
    }


    @Override
    public RefBranchType getById(Long id) {
        RefBranchType refBranchType = repo.findById(id).get();
        return refBranchType;
    }

    @Override
    public RefBranchType getByCode(String code) {
        RefBranchType refBranchType = repo.findByCode(code);
        return refBranchType;
    }

    @Override
    public List<RefBranchType> getAllRefBranchTypes() {
        List<RefBranchType> refBranchTypes = repo.findAll();
        return refBranchTypes;
    }

    @Override
    public RefBranchType addRefBranchType(RefBranchType refBranchType) {
        RefBranchType result = repo.save(refBranchType);
        return result;
    }

    @Override
    public RefBranchType updateRefBranchType(RefBranchType refBranchType) {
        RefBranchType result = repo.save(refBranchType);
        return result;
    }

    @Override
    public void deleteRefBranchType(RefBranchType refBranchType) {
        repo.delete(refBranchType);
    }

    @Override
    public void deleteRefBranchType(Long id) {
        repo.deleteById(id);
    }
}
