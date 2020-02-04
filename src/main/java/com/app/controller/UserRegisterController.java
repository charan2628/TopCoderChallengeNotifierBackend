package com.app.controller;

import java.lang.invoke.MethodHandles;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.User;
import com.app.service.UserService;
import com.app.util.AppUtil;

@CrossOrigin
@RestController
@RequestMapping("/register")
public class UserRegisterController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());
    
    private UserService userService;
    private String salt;
    
    public UserRegisterController(
            UserService userService,
            @Value("${SALT}") String salt) {
        this.userService = userService;
        this.salt = salt;
    }
    
    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public void registerUser(@RequestBody User user) throws Exception {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("POST REQUEST /register BODY: {}", user);
        } else {
            LOGGER.debug("POST REQUEST /register");
        }
        user.setConfirmed(false);
        user.setId(null);
        user.setConfirmToken(null);
        user.setPassword(AppUtil.sha256(user.getPassword() + salt));
        this.userService.addUser(user);
    }
    
    @PostMapping(path = "/confirm", consumes = "application/json", produces = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public void confirmRegistration(
            @PathParam("email") String email,
            @PathParam("code") String code) {
        if(email == null || code == null || code.equals("") || email.equals("")) {
            throw new IllegalArgumentException();
        }
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("POST REQUEST /register/confirm BODY: {} {}", email, code);
        } else {
            LOGGER.debug("POST REQUEST /register/cofirm");
        }
        this.userService.confirmUser(email, code);
    }
}
