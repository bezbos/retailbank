package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.repository.RefTransactionTypeRepository;
import com.codecentric.retailbank.service.interfaces.IRefTransactionTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RefTransactionTypeService implements IRefTransactionTypeService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefTransactionTypeRepository repo;


    public RefTransactionTypeService() {
        super();
    }


    @Override
    public RefTransactionType getById(Long id) {
        RefTransactionType refTransactionType = repo.getOne(id);
        return refTransactionType;
    }

    @Override
    public RefTransactionType getByCode(String code) {
        RefTransactionType refTransactionType = repo.findByCode(code);
        return refTransactionType;
    }

    @Override
    public List<RefTransactionType> getAllRefTransactionTypes() {
        List<RefTransactionType> refTransactionTypes = repo.findAll();
        return refTransactionTypes;
    }

    @Override
    public RefTransactionType addRefTransactionType(RefTransactionType refTransactionType) {
        RefTransactionType result = repo.save(refTransactionType);
        return result;
    }

    @Override
    public RefTransactionType updateRefTransactionType(RefTransactionType refTransactionType) {
        RefTransactionType result = repo.save(refTransactionType);
        return result;
    }

    @Override
    public void deleteRefTransactionType(RefTransactionType refTransactionType) {
        repo.delete(refTransactionType);
    }

    @Override
    public void deleteRefTransactionType(Long id) {
        repo.deleteById(id);
    }
}
