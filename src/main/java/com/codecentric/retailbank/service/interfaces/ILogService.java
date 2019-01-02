package com.codecentric.retailbank.service.interfaces;

public interface ILogService {
    void persistLog(String message);

    void persistLog(Exception exception);
}
