package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.ChallengeService;

@CrossOrigin
@RestController
@RequestMapping("/challenge")
public class ChallengeController {
	
	private ChallengeService challengeService;
	
	public ChallengeController(@Autowired ChallengeService challengeService) {
		this.challengeService = challengeService;
	}

	@DeleteMapping(path = "", produces = "application/json", consumes = "application/json")
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteOldChallenges() {
		this.challengeService.deleteAll();
	}
}
