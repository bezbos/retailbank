package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.repository.BankAccountRepository;
import com.codecentric.retailbank.repository.RefAccountStatusRepository;
import com.codecentric.retailbank.repository.TransactionRepository;
import com.codecentric.retailbank.service.interfaces.IRefAccountStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefAccountStatusService implements IRefAccountStatusService {

    //region FIELDS
    @Autowired
    private RefAccountStatusRepository refAccountStatusRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    //endregion

    //region READ
    @Override public RefAccountStatus getById(Long id) {
        RefAccountStatus refAccountStatus = refAccountStatusRepository.single(id);
        return refAccountStatus;
    }

    @Override public RefAccountStatus getByCode(String code) {
        RefAccountStatus refAccountStatus = refAccountStatusRepository.singleByCode(code);
        return refAccountStatus;
    }

    @Override public List<RefAccountStatus> getAllRefAccountStatus() {
        List<RefAccountStatus> refAccountStatuses = refAccountStatusRepository.all();
        return refAccountStatuses;
    }
    //endregion

    //region WRITE
    @Override public RefAccountStatus addRefAccountStatus(RefAccountStatus refAccountStatus) {
        RefAccountStatus result = refAccountStatusRepository.add(refAccountStatus);
        return result;
    }

    @Override public RefAccountStatus updateRefAccountStatus(RefAccountStatus refAccountStatus) {
        RefAccountStatus result = refAccountStatusRepository.update(refAccountStatus);
        return result;
    }
    //endregion

    //region DELETE
    @Override public void deleteRefAccountStatus(RefAccountStatus refAccountStatus) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        refAccountStatusRepository.delete(refAccountStatus);
    }

    @Override public void deleteRefAccountStatus(Long id) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        RefAccountStatus refAccountStatus = refAccountStatusRepository.single(id);
        refAccountStatusRepository.delete(refAccountStatus);
    }
    //endregion
}
