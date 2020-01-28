package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.app.AppRunner;
import com.app.model.Challenge;

@ActiveProfiles("test")
@SpringBootTest(classes = AppRunner.class)
public class ChallengeServiceTest extends AbstractTestNGSpringContextTests{

	@Autowired
	private ChallengeService challengeService;
	
	@Test
	public void challengesCountTest() {
		this.challengeService.deleteAll();
		Assert.assertEquals(this.challengeService.getChallengesCount(), 0L);
		Challenge challenge = new Challenge("Backend Challange", "Integrate API to FrontEnd", "https://topcoder.com/challenge/12345");
		this.challengeService.addChallenge(challenge);
		Assert.assertEquals(this.challengeService.getChallengesCount(), 1L);
	}
	
	@Test
	public void challengeInsertTest() {
		this.challengeService.deleteAll();
		Challenge challenge = new Challenge("Backend Challange", "Integrate API to FrontEnd", "https://topcoder.com/challenge/12345");
		this.challengeService.addChallenge(challenge);
		Assert.assertTrue(this.challengeService.isPresent(challenge.getName()));
	}
	
	@Test
	public void challengeInsertTest2() {
		this.challengeService.deleteAll();
		Challenge challenge = new Challenge("Backend Challange", "Integrate API to FrontEnd", "https://topcoder.com/challenge/12345");
		Challenge challenge2 = new Challenge("FrontEnd Challange", "Add new search to FrontEnd", "https://topcoder.com/challenge/12346");
		this.challengeService.addChallenge(challenge);
		this.challengeService.addChallenge(challenge2);
		List<Challenge> challenges = new ArrayList<>();
		challenges.add(challenge2); challenges.add(challenge);
		Assert.assertEqualsNoOrder(this.challengeService.getChallenges().toArray(), challenges.toArray());
	}
}
