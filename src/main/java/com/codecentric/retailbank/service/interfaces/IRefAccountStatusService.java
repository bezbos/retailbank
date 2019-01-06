package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.RefAccountStatus;

import java.util.List;

public interface IRefAccountStatusService {

    // GET
    RefAccountStatus getById(Long id);

    RefAccountStatus getByCode(String code);

    List<RefAccountStatus> getAllRefAccountStatus();

    // CREATE
    RefAccountStatus addRefAccountStatus(RefAccountStatus refAccountStatus);

    // UPDATE
    RefAccountStatus updateRefAccountStatus(RefAccountStatus refAccountStatus);

    // DELETE
    void deleteRefAccountStatus(RefAccountStatus refAccountStatus);

    void deleteRefAccountStatus(Long id);
}
