package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.RefTransactionType;

import java.util.List;

public interface IRefTransactionTypeService {

    RefTransactionType getById(Long id);

    RefTransactionType getByCode(String code);

    List<RefTransactionType> getAllRefTransactionTypes();

    RefTransactionType addRefTransactionType(RefTransactionType refTransactionType);

    RefTransactionType updateRefTransactionType(RefTransactionType refTransactionType);

    void deleteRefTransactionType(RefTransactionType refTransactionType);

    void deleteRefTransactionType(Long id);
}
