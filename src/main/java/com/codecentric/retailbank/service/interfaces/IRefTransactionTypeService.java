package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.OLD.RefTransactionType;

import java.util.List;

public interface IRefTransactionTypeService {

    // GET
    RefTransactionType getById(Long id);

    RefTransactionType getByCode(String code);

    List<RefTransactionType> getAllRefTransactionTypes();

    // CREATE
    RefTransactionType addRefTransactionType(RefTransactionType refTransactionType);

    // UPDATE
    RefTransactionType updateRefTransactionType(RefTransactionType refTransactionType);

    // DELETE
    void deleteRefTransactionType(RefTransactionType refTransactionType);

    void deleteRefTransactionType(Long id);
}
