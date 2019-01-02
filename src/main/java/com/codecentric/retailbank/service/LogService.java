package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.security.Log;
import com.codecentric.retailbank.repository.LogRepository;
import com.codecentric.retailbank.service.interfaces.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService implements ILogService {
    @Autowired
    LogRepository logRepository;


    @Override
    public void persistLog(String message) {
        logRepository.save(Log.generateLog(message));
    }

    @Override
    public void persistLog(Exception exception) {
        logRepository.save(Log.generateLog(exception));
    }
}
