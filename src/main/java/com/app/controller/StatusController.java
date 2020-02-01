package com.app.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Status;
import com.app.service.ErrorLogService;
import com.app.service.StatusService;

/**
 * Controller for /status requests.
 *
 * @author charan2628
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/status")
public class StatusController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());
    
    private StatusService statusService;
    private ErrorLogService errorLogService;

    public StatusController(
            @Autowired StatusService statusService,
            @Autowired ErrorLogService errorLogService) {
        this.statusService = statusService;
        this.errorLogService = errorLogService;
    }

    /**
     * Get mapping for /status request
     *
     * @return
     */
    @GetMapping(
            path = "",
            produces = "application/json")
    public Status getStatus() {
        LOGGER.info("GET REQUEST /status");
        return this.statusService.getStatus();
    }

    /**
     * Get mapping for /status/errors request
     *
     * @return the list of error messages
     */
    @GetMapping(
            path = "errors",
            produces = "application/json")
    public List<String> getErrorMessages() {
        LOGGER.info("GET REWUEST /status/errors");
        return this.errorLogService.getErrorLogs();
    }
}
