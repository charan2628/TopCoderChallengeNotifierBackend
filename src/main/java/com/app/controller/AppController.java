package com.app.controller;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AppController {
	
	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@GetMapping("")
	public String main() {
		logger.info("GET /");
		return "RUNNING!";
	}
}
