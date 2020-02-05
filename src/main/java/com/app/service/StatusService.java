package com.app.service;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.app.dao.StatusDao;
import com.app.model.Status;

/**
 * Service class for getting, updating the
 * status of the application.
 *
 * @author charan2628
 *
 */
@Service
public class StatusService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private StatusDao statusDao;

    public StatusService(
            @Autowired StatusDao statusDao) {
        this.statusDao = statusDao;
    }

    /**
     * Get the current status from db.
     *
     * @return
     */
    public Status getStatus() {
        Status status = null;
        try {
            status = this.statusDao.getStatus();
        } catch (Exception exception) {
            LOGGER.error("Error retrieving status {}", exception);
            throw exception;
        }
        LOGGER.debug("Got Status {}", status);
        return status;
    }

    /**
     * Asynchronously updates the task status without
     * holding of the client request in a separate thread pool
     * configured in {@code AppConfig}.
     *
     * @param taskStatus
     *        task status true for success, false for fail
     */
    @Async("asnc_executor")
    public void taskStatus(boolean taskStatus) {
        try {
            Status status = this.getStatus();
            if (status == null) {
                status = new Status();
            }
            if (taskStatus) {
                status.setSuccessfullTasks(
                        status.getSuccessfullTasks()+1);
            } else {
                status.setFailedTasks(
                        status.getFailedTasks() + 1);
            }
            status.setId(null);
            this.statusDao.addStatus(status);
        } catch (Exception e) {
            LOGGER.error("Error updating taskStatus: {} {}", taskStatus, e);
            return;
        }
    }

    /**
     * Asynchronously updates the error status without
     * holding of the client request in a separate thread pool
     * configured in {@code AppConfig}.
     *
     */
    @Async("async_executor")
    public void error() {
        try {
            Status status = this.getStatus();
            if (status == null) {
                status = new Status();
            }
            status.setErrors(
                    status.getErrors() + 1);
            status.setId(null);
            this.statusDao.addStatus(status);
        } catch (Exception e) {
            LOGGER.error("Error updating error status: {}", e);
            return;
        }
    }
}
