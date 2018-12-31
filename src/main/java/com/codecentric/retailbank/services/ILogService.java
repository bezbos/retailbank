package com.codecentric.retailbank.services;

public interface ILogService {
    void persistLog(String message);

    void persistLog(Exception exception);
}
