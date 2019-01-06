package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.repository.CustomerRepository;
import com.codecentric.retailbank.repository.RefAccountStatusRepository;
import com.codecentric.retailbank.service.interfaces.IRefAccountStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RefAccountStatusService implements IRefAccountStatusService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefAccountStatusRepository repo;


    public RefAccountStatusService() {
        super();
    }


    @Override
    public RefAccountStatus getById(Long id) {
        RefAccountStatus refAccountStatus = repo.getOne(id);
        return refAccountStatus;
    }

    @Override
    public RefAccountStatus getByCode(String code) {
        RefAccountStatus refAccountStatus = repo.getByCode(code);
        return refAccountStatus;
    }

    @Override
    public List<RefAccountStatus> getAllRefAccountStatus() {
        List<RefAccountStatus> refAccountStatuses = repo.findAll();
        return refAccountStatuses;
    }

    @Override
    public RefAccountStatus addRefAccountStatus(RefAccountStatus refAccountStatus) {
        RefAccountStatus result = repo.save(refAccountStatus);
        return result;
    }

    @Override
    public RefAccountStatus updateRefAccountStatus(RefAccountStatus refAccountStatus) {
        RefAccountStatus result = repo.save(refAccountStatus);
        return result;
    }

    @Override
    public void deleteRefAccountStatus(RefAccountStatus refAccountStatus) {
        repo.delete(refAccountStatus);
    }

    @Override
    public void deleteRefAccountStatus(Long id) {
        repo.deleteById(id);
    }
}
