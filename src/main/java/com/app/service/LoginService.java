package com.app.service;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.exception.UnAuthorizedException;
import com.app.exception.UnConfirmedRegistrationExcpetion;
import com.app.model.Login;
import com.app.model.Token;
import com.app.model.User;
import com.app.model.jwt.Claims;
import com.app.util.AppUtil;

@Service
public class LoginService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private UserService userService;
    private AccessTokenService accessTokenService;
    private String salt;
    private String issuer;
    private String tokenExpTime;
    private ZoneOffset zoneOffset;
    
    public LoginService(
            UserService userService,
            AccessTokenService accessTokenService,
            @Value("${SALT}") String salt,
            @Value("${ISSUER}") String issuer,
            @Value("${TOKEN_EXP_TIME}") String tokenExpTime) {
        this.userService = userService;
        this.accessTokenService = accessTokenService;
        this.salt = salt;
        this.issuer = issuer;
        this.tokenExpTime = tokenExpTime;
        this.zoneOffset = ZoneId.systemDefault()
                .getRules()
                .getOffset(Instant.now());
    }

    public boolean verifyLogin(Login login) throws Exception {
        login.setPassword(
                AppUtil.sha256(login.getPassword() + salt));
        User user = this.userService.getUser(login.getEmail());
        if(!user.isConfirmed()) {
            throw new UnConfirmedRegistrationExcpetion();
        }
        if(user.getPassword().equals(login.getPassword())) {
            return true;
        }
        LOGGER.info("Unauthorized request {}", login);
        return false;
    }

    public Token getToken(Login login, boolean isAdmin) throws Exception {
        if(!this.verifyLogin(login)) {
            throw new UnAuthorizedException();
        }
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime.plusMinutes(Long.valueOf(this.tokenExpTime));
        Claims claims = new Claims(this.issuer, dateTime.toEpochSecond(zoneOffset), login.getEmail(), isAdmin);
        Token token = new Token();
        token.setAccessToken(
                this.accessTokenService.createToken(claims));
        return token;
    }
    
    public boolean isAdmin(Login login) {
        User user = this.userService.getUser(login.getEmail());
        if(user.isAdmin()) {
            LOGGER.info("Admin login: {}", login.getEmail());
            return true;
        } else {
            LOGGER.info("Unauthorized Admin login: {}", login.getEmail());
            return false;
        }
    }
}
