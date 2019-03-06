package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.repository.BankAccountRepository;
import com.codecentric.retailbank.repository.RefAccountStatusRepository;
import com.codecentric.retailbank.repository.TransactionRepository;
import com.codecentric.retailbank.service.interfaces.IRefAccountStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RefAccountStatusService implements IRefAccountStatusService {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final RefAccountStatusRepository refAccountStatusRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    //endregion

    //region CONSTRUCTOR
    @Autowired public RefAccountStatusService(RefAccountStatusRepository refAccountStatusRepository,
                                              BankAccountRepository bankAccountRepository,
                                              TransactionRepository transactionRepository) {
        this.refAccountStatusRepository = refAccountStatusRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
    }
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
        // Recursively find and delete any FK constraints to this refAccountStatus
//        bankAccountRepository.findByStatus(refAccountStatus).forEach(bankAccount -> {
//            transactionRepository.findByAccount(bankAccount).forEach(transaction -> {
//                transactionRepository.delete(transaction);
//            });
//            bankAccountRepository.delete(bankAccount);
//        });

        // Delete the actual refAccountStatus
        refAccountStatusRepository.delete(refAccountStatus);
    }

    @Override public void deleteRefAccountStatus(Long id) {
        RefAccountStatus refAccountStatus = refAccountStatusRepository.single(id);

        // Recursively find and delete any FK constraints to this refAccountStatus
//        bankAccountRepository.findByStatus(refAccountStatus).forEach(bankAccount -> {
//            transactionRepository.findByAccount(bankAccount).forEach(transaction -> {
//                transactionRepository.delete(transaction);
//            });
//            bankAccountRepository.delete(bankAccount);
//        });

        // Delete the actual refAccountStatus
        refAccountStatusRepository.delete(refAccountStatus);
    }
    //endregion
}
