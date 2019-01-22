package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.RefAccountStatus;

import java.util.List;

public interface IRefAccountStatusService {

    RefAccountStatus getById(Long id);

    RefAccountStatus getByCode(String code);

    List<RefAccountStatus> getAllRefAccountStatus();

    RefAccountStatus addRefAccountStatus(RefAccountStatus refAccountStatus);

    RefAccountStatus updateRefAccountStatus(RefAccountStatus refAccountStatus);

    void deleteRefAccountStatus(RefAccountStatus refAccountStatus);

    void deleteRefAccountStatus(Long id);
}
