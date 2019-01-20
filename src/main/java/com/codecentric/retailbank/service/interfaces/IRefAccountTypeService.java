package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.RefAccountType;

import java.util.List;

public interface IRefAccountTypeService {

    // GET
    RefAccountType getById(Long id);

    RefAccountType getByCode(String code);

    List<RefAccountType> getAllRefAccountTypes();

    // CREATE
    RefAccountType addRefAccountType(RefAccountType refAccountType);

    // UPDATE
    RefAccountType updateRefAccountType(RefAccountType refAccountType);

    // DELETE
    void deleteRefAccountType(RefAccountType refAccountType);

    void deleteRefAccountType(Long id);
}
