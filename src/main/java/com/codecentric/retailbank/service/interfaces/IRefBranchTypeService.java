package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.RefBranchType;

import java.util.List;

public interface IRefBranchTypeService {

    RefBranchType getById(Long id);

    RefBranchType getByCode(String code);

    List<RefBranchType> getAllRefBranchTypes();

    RefBranchType addRefBranchType(RefBranchType refBranchType);

    RefBranchType updateRefBranchType(RefBranchType refBranchType);

    void deleteRefBranchType(RefBranchType refBranchType);

    void deleteRefBranchType(Long id);
}
