package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.RefAccountType;

import java.util.List;

public interface IRefAccountTypeService {

    RefAccountType getById(Long id);

    RefAccountType getByCode(String code);

    List<RefAccountType> getAllRefAccountTypes();

    RefAccountType addRefAccountType(RefAccountType refAccountType);

    RefAccountType updateRefAccountType(RefAccountType refAccountType);

    void deleteRefAccountType(RefAccountType refAccountType);

    void deleteRefAccountType(Long id);
}
