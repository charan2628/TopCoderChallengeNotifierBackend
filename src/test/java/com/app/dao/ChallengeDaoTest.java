package com.app.dao;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.app.AppRunner;
import com.app.model.Challenge;
import com.mongodb.MongoClient;

@SpringBootTest(classes = AppRunner.class)
public class ChallengeDaoTest extends AbstractTestNGSpringContextTests{
	
	@Autowired 
	private MongoClient client;
	
	@Autowired
	private ChallengeDao challengeDao;
	
	@Test
	public void challengesCountTest() {
		Assert.assertNotNull(client);
		this.deleteCollection();
		Assert.assertEquals(this.challengeDao.getChallengesCount(), 0L);
	}
	
	@Test
	public void challengeInsertTest() {
		this.deleteCollection();
		Challenge challenge = new Challenge("Bakcend Challange", "Integrate API to FrontEnd", "https://topcoder.com/challenge/12345");
		this.challengeDao.addchallenge(challenge);
		Assert.assertEquals(this.challengeDao.getChallengesCount(), 1L);
		Assert.assertTrue(this.challengeDao.isPresent(challenge.getName()));
	}
	
	private void deleteCollection() {
		this.client.getDatabase("test").getCollection("challenges").deleteMany(new Document());
	}
}
