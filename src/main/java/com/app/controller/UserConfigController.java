package com.app.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.UserConfig;
import com.app.model.jwt.Claims;
import com.app.service.AccessTokenService;
import com.app.service.UserConfigService;

@CrossOrigin
@RestController
@RequestMapping("/config")
public class UserConfigController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private AccessTokenService accessTokenService; 
    private UserConfigService userConfigService;

    public UserConfigController(
            AccessTokenService accessTokenService,
            UserConfigService userConfigService) {
        this.accessTokenService = accessTokenService;
        this.userConfigService = userConfigService;
    }
    
    @GetMapping(path = "", produces = "application/json")
    public UserConfig getUserConfig(HttpServletRequest request) throws Exception {
        LOGGER.info("GET REQUEST /config");
        String token = request.getHeader("Authorization");
        Claims claims = this.accessTokenService.getClaims(token);
        return this.userConfigService.getUserConfig(claims.getEmail());
    }
    
    @PostMapping(path = "tags", consumes = "application/json", produces = "application/json")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void updateTags(
            @RequestBody List<String> tags,
            HttpServletRequest request) throws Exception {
        LOGGER.info("POST REQUEST /config/tags");
        String token = request.getHeader("Authorization");
        Claims claims = this.accessTokenService.getClaims(token);
        this.userConfigService.updateTags(claims.getEmail(), tags);
    }
    
    @PostMapping(path = "schedule", consumes = "application/json", produces = "application/json")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void updateSchedule(
            @RequestBody String time,
            HttpServletRequest request) throws Exception {
        LOGGER.info("POST REQUEST /config/tags");
        String token = request.getHeader("Authorization");
        Claims claims = this.accessTokenService.getClaims(token);
        this.userConfigService.updateSchedule(claims.getEmail(), Long.parseLong(time));
    }
}
