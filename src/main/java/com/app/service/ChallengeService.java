package com.app.service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ChallengeDao;
import com.app.model.Challenge;

@Service
public class ChallengeService {
	
	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private ChallengeDao challengeDao;
	
	public List<Challenge> getChallenges() {
		List<Challenge> challenges = new ArrayList<>();
		try {
			challenges = this.challengeDao.getchallenges();
		} catch (Exception exception) {
			logger.error("Error retrieving challenges {}", exception);
			throw exception;
		}
		logger.debug("Retrieved challenges {}", challenges);
		return challenges;
	}
	
	public List<Challenge> getChallenges(String challengeName) {
		List<Challenge> challenges = new ArrayList<>();
		try {
			challenges = this.challengeDao.getChallenges(challengeName);
		} catch (Exception exception) {
			logger.error("Error retrieving challenges name: {} {}", challengeName, exception);
			throw exception;
		}
		logger.debug("Retrieved challenges: {} name: {}", challenges, challengeName);
		return challenges;
	}
	
	public void addChallenge(Challenge challenge) {
		try {
			this.challengeDao.addchallenge(challenge);
		} catch (Exception exception) {
			logger.error("Error inserting challenge {} {}", challenge, exception);
			throw exception;
		}
		logger.debug("Added challenge: {} successfully", challenge);
	}
	
	public void addChallenges(List<Challenge> challenges) {
		try {
			this.challengeDao.addChallenges(challenges);
		} catch (Exception exception) {
			logger.error("Error inserting challenges: {} {}", challenges, exception);
			throw exception;
		}
		logger.debug("Added challenges: {} successfully", challenges);
	}
	
	public long getChallengesCount() {
		long count = 0;
		try {
			count =  this.challengeDao.getChallengesCount();
		} catch (Exception exception) {
			logger.error("Error getting count of challenges {}", exception);
			throw exception;
		}
		logger.debug("Got challenges count: {}", count);
		return count;
		
	}
	
	public boolean isPresent(String challengeName) {
		boolean isPresent = false;
		try {
			isPresent = this.challengeDao.isPresent(challengeName);
		} catch (Exception exception) {
			logger.error("Error checking database {}", exception);
			throw exception;
		}
		logger.debug("challenge: {} isPresent: {}", challengeName, isPresent);
		return isPresent;
	}
	
	public boolean arePresent(List<String> challengeNames) {
		boolean arePresent = false;
		try {
			arePresent = this.challengeDao.arePresent(challengeNames);
		} catch (Exception exception) {
			logger.error("Error checking database [arePresent] {}", exception);
			throw exception;
		}
		logger.debug("challenges: {} arePresnt: {}", challengeNames, arePresent);
		return  arePresent;
	}
	
	public void deleteAll() {
		try {
			this.challengeDao.deleteAll();
		} catch (Exception exception) {
			logger.error("Error deleting challenges {}", exception);
			throw exception;
		}
		logger.debug("Deleted challenges successfully");
	}
}
