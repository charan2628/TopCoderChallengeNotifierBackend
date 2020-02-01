package com.app.controller;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Status;
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
    
    public StatusController(
            @Autowired StatusService statusService) {
        this.statusService = statusService;
    }

    /**
     * Get mapping for /status requests
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
}
