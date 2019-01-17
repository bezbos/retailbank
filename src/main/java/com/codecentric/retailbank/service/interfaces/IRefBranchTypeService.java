package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.RefBranchType;

import java.util.List;

public interface IRefBranchTypeService {

    // GET
    RefBranchType getById(Long id);

    RefBranchType getByCode(String code);

    List<RefBranchType> getAllRefBranchTypes();

    // CREATE
    RefBranchType addRefBranchType(RefBranchType refBranchType);

    // UPDATE
    RefBranchType updateRefBranchType(RefBranchType refBranchType);

    // DELETE
    void deleteRefBranchType(RefBranchType refBranchType);

    void deleteRefBranchType(Long id);
}
