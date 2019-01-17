package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.OLD.RefBranchTypeOLD;

import java.util.List;

public interface IRefBranchTypeService {

    // GET
    RefBranchTypeOLD getById(Long id);

    RefBranchTypeOLD getByCode(String code);

    List<RefBranchTypeOLD> getAllRefBranchTypes();

    // CREATE
    RefBranchTypeOLD addRefBranchType(RefBranchTypeOLD refBranchType);

    // UPDATE
    RefBranchTypeOLD updateRefBranchType(RefBranchTypeOLD refBranchType);

    // DELETE
    void deleteRefBranchType(RefBranchTypeOLD refBranchType);

    void deleteRefBranchType(Long id);
}
