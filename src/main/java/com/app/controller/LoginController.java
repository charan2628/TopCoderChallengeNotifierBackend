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

import com.app.exception.UnAuthorizedException;
import com.app.model.Login;
import com.app.model.Token;
import com.app.service.AccessTokenService;
import com.app.service.AuthorizationService;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private AuthorizationService authorizationService;
    private AccessTokenService accessTokenService;
    
    public LoginController(
            @Autowired AuthorizationService authorizationService,
            @Autowired AccessTokenService accessTokenService) {
        
        this.authorizationService = authorizationService;
        this.accessTokenService = accessTokenService;
    }
    
    @PostMapping
    public Token login(@RequestBody Login login) throws Exception{
        LOGGER.info("GET /login");
        if(authorizationService.authenticate(login)) {
            LOGGER.debug("USER AUTHENTICATED");
            return new Token(this.accessTokenService.createToken());
        }
        LOGGER.info("USER UNAUTHENTICATED");
        throw new UnAuthorizedException();
    }
}
