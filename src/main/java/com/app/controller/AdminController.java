package com.app.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.UnAuthorizedException;
import com.app.model.Login;
import com.app.model.Status;
import com.app.model.Token;
import com.app.service.ErrorLogService;
import com.app.service.LoginService;
import com.app.service.StatusService;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private LoginService loginService;
    private StatusService statusService;
    private ErrorLogService errorLogService;
    
    public AdminController(
            LoginService loginService, StatusService statusService,
            ErrorLogService errorLogService) {
        this.loginService = loginService;
        this.statusService = statusService;
        this.errorLogService = errorLogService;
    }

    @PostMapping(
            path = "",
            produces = "application/json", consumes = "application/json")
    public Token adminLogin(@RequestBody Login login) throws Exception {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("POST /login BODY: {}", login);
        } else {
            LOGGER.info("POST /login");
        }
        if(!this.loginService.isAdmin(login)) {
            throw new UnAuthorizedException();
        } else {
            return this.loginService.getToken(login, true);
        }
    }
    
    @GetMapping(
            path = "status",
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
            path = "status/errors",
            produces = "application/json")
    public List<String> getErrorMessages() {
        LOGGER.info("GET REWUEST /status/errors");
        return this.errorLogService.getErrorLogs();
    }
}
