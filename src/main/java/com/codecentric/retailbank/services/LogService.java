package com.codecentric.retailbank.services;

import com.codecentric.retailbank.persistence.dao.LogRepository;
import com.codecentric.retailbank.persistence.model.Log;
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
