package com.app.controller;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Config;
import com.app.service.ConfigService;

/**
 * Controller for /config requests.
 * 
 * @author charan2628
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/config")
public class ConfigController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());
            
    private ConfigService configService;
    
    public ConfigController(@Autowired ConfigService configService) {
        LOGGER.debug("Config controller initialization started");
        this.configService = configService;
        LOGGER.debug("Config controller initialized");
    }
    
    @GetMapping(path = "", produces = "application/json")
    public Config getConfig() {
        LOGGER.info("GET /config");
        return this.configService.getConfig();
    }
    
    @PostMapping(path = "", produces = "application/json", consumes = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public void addConfig(@RequestBody Config config) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("POST /config BODY: {}", config.toString());
        } else {
            LOGGER.info("POST /config");
        }
        this.configService.addConfig(config);
    }
    
    @PutMapping(path = "", produces = "application/json", consumes = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateConfig(@RequestBody Config config) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("PUT /config BODY: {}", config.toString());
        } else {
            LOGGER.info("PUT /config");
        }
        if(config.getEmails() != null && config.getEmails().size() > 0) {
            this.configService.updateEmails(config.getEmails());
        }
        if(config.getTags() != null && config.getTags().size() > 0) {
            this.configService.updateTags(config.getTags());
        }
    }
    
    @DeleteMapping(path = "", produces = "application/json", consumes = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteConfig(@RequestBody Config config) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("PUT /config BODY: {}", config.toString());
        } else {
            LOGGER.info("PUT /config");
        }
        if(config.getEmails() != null && config.getEmails().size() > 0) {
            this.configService.deleteEmails(config.getEmails());
        }
        if(config.getTags() != null && config.getTags().size() > 0) {
            this.configService.deleteTags(config.getTags());
        }
    }
}
