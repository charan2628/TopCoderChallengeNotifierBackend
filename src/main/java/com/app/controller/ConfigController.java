package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Config;
import com.app.service.ConfigService;

@RestController
@RequestMapping("/config")
public class ConfigController {

	private ConfigService configService;
	
	public ConfigController(
			@Autowired ConfigService configService) {
		this.configService = configService;
	}
	
	@GetMapping(path = "", produces = "application/json")
	public Config getConfig() {
		return this.configService.getConfig();
	}
	
	@PostMapping(path = "", produces = "application/json", consumes = "application/json")
	public String addConfig(
			@RequestBody Config config) {
		this.configService.addConfig(config);
		return "ADDED CONFIG";
	}
	
	@PutMapping(path = "", produces = "application/json", consumes = "application/json")
	public String updateConfig(
			@RequestBody Config config) {
		this.configService.updateEmails(config.getEmails());
		this.configService.updateTags(config.getTags());
		return "UPDATED";
	}
}
