package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.ChallengeService;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {
	
	private ChallengeService challengeService;
	
	public ChallengeController(
			@Autowired ChallengeService challengeService) {
		this.challengeService = challengeService;
	}

	@DeleteMapping(path = "", produces = "application/json", consumes = "application/json")
	public String deleteOldChallenges() {
		this.challengeService.deleteAll();
		return "DELETED";
	}
}
