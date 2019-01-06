package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefAccountType;
import com.codecentric.retailbank.repository.BankRepository;
import com.codecentric.retailbank.repository.RefAccountTypeRepository;
import com.codecentric.retailbank.service.interfaces.IRefAccountTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RefAccountTypeService implements IRefAccountTypeService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefAccountTypeRepository repo;


    public RefAccountTypeService() {
        super();
    }


    @Override
    public RefAccountType getById(Long id) {
        RefAccountType refAccountType = repo.getOne(id);
        return refAccountType;
    }

    @Override
    public RefAccountType getByCode(String code) {
        RefAccountType refAccountType = repo.findByCode(code);
        return refAccountType;
    }

    @Override
    public List<RefAccountType> getAllRefAccountTypes() {
        List<RefAccountType> refAccountTypes = repo.findAll();
        return refAccountTypes;
    }

    @Override
    public RefAccountType addRefAccountType(RefAccountType refAccountType) {
        RefAccountType result = repo.save(refAccountType);
        return result;
    }

    @Override
    public RefAccountType updateRefAccountType(RefAccountType refAccountType) {
        RefAccountType result = repo.save(refAccountType);
        return result;
    }

    @Override
    public void deleteRefAccountType(RefAccountType refAccountType) {
        repo.delete(refAccountType);
    }

    @Override
    public void deleteRefAccountType(Long id) {
        repo.deleteById(id);
    }
}
