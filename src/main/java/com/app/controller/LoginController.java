package com.app.controller;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Login;
import com.app.model.Token;
import com.app.service.LoginService;

/**
 * Controller for /login requests.
 *
 * @author charan2628
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private LoginService loginService;

    public LoginController(
            @Autowired LoginService loginService) {
        
        this.loginService = loginService;
    }

    @PostMapping(
            path = "",
            produces = "application/json", consumes = "application/json")
    public Token login(@RequestBody Login login) throws Exception{
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("POST /login BODY: {}", login);
        } else {
            LOGGER.info("POST /login");
        }
        return this.loginService.getToken(login);
    }
}
