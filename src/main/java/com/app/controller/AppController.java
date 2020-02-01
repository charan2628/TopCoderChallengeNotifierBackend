package com.app.controller;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Application Landing controller can be used
 * to check whether application is running.
 *
 * @author charan2628
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/")
public class AppController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    /**
     * GET mapping for /.
     */
    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public void main() {
        LOGGER.info("GET /");
    }
}
