package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ChallengeDao;
import com.app.model.Challenge;

@Service
public class ChallengeService {

	@Autowired
	private ChallengeDao challengeDao;
	
	public List<Challenge> getChallenges() {
		return this.challengeDao.getchallenges();
	}
	
	public List<Challenge> getChallenges(String challengeName) {
		return this.challengeDao.getChallenges(challengeName);
	}
	
	public void addChallenge(Challenge challenge) {
		this.challengeDao.addchallenge(challenge);
	}
	
	public void addChallenges(List<Challenge> challenges) {
		this.challengeDao.addChallenges(challenges);
	}
	
	public long getChallengesCount() {
		return this.challengeDao.getChallengesCount();
	}
	
	public boolean isPresent(String challengeName) {
		return this.challengeDao.isPresent(challengeName);
	}
	
	public boolean arePresent(List<String> challengeNames) {
		return this.challengeDao.arePresent(challengeNames);
	}
}
