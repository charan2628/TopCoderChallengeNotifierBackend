package com.app.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ErrorPageController implements ErrorController {
    
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "NOT_SUPPORTED";
    }
    
    @Override
    public String getErrorPath() {
        return PATH;
    }

}
