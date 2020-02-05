package com.app.service;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.app.dao.ErrorLogDao;

/**
 * Service class to add and get error logs
 *
 * @author charan2628
 *
 */
@Service
public class ErrorLogService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private ErrorLogDao errorLogDao;

    public ErrorLogService(
            @Autowired ErrorLogDao errorLogDao) {
        this.errorLogDao = errorLogDao;
    }

    /**
     * Get the errors logged in db.
     *
     * @return the list of error logs
     */
    public List<String> getErrorLogs() {
        try {
            return this.errorLogDao.getErrorLogs();
        } catch (Exception e) {
            LOGGER.error("Error getting error logs {}", e);
            throw e;
        }
    }

    /**
     * Asynchronously adds log to db without
     * holding of the client request in a separate thread pool
     * configured in {@code AppConfig}.
     *
     * @param errorLog error log message
     */
    @Async("async_executor")
    public void addErrorLog(String errorLog) {
        try {
            this.errorLogDao.addErrorLog(errorLog);
        } catch (Exception e) {
            LOGGER.error("Error adding log to db {}", e);
        }
    }
}
