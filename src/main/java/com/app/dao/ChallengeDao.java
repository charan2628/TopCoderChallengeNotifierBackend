package com.app.dao;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.app.model.Challenge;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;;

@Repository
public class ChallengeDao {

	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private MongoCollection<Challenge> collection;

	public ChallengeDao(
			MongoClient mongoClient, 
			@Value("${app.database}") String databaseName,
			@Value("${app.collection.challenges}") String challenges) {

		this.collection = mongoClient.getDatabase(databaseName).getCollection(challenges, Challenge.class);
		logger.debug("ChallengeDao initialized successfully DB: {} COLLECTION: {}", databaseName, challenges);
	}

	public void addchallenge(Challenge challenge) {
		try {
			this.collection.insertOne(challenge);
		} catch (Exception e) {
			logger.error("Error inserting challenge {} {}", challenge, e);
			return;
		}
		logger.debug("Added challenge: {} successfully", challenge);
	}

	public void addChallenges(List<Challenge> challenges) {
		try {
			this.collection.insertMany(challenges);
		} catch (Exception e) {
			logger.error("Error inserting challenges: {} {}", challenges, e);
			return;
		}
		logger.debug("Added challenges: {} successfully", challenges);
	}

	public List<Challenge> getChallenges(String challengeName) {
		List<Challenge> challenges = new ArrayList<>();
		try {
			this.collection.find(eq("name", challengeName))
			.forEach((Consumer<Challenge>)(challenge) -> challenges.add(challenge));
		} catch (Exception e) {
			logger.error("Error retrieving challenges name: {} {}", challengeName, e);
			return challenges;
		}
		logger.debug("Retrieved challenges: {} name: {}", challenges, challengeName);
		return challenges;
	}

	public List<Challenge> getchallenges() {
		List<Challenge> challenges = new ArrayList<>();
		try {
			this.collection.find()
			.forEach((Consumer<Challenge>)(challenge) -> challenges.add(challenge));
		} catch (Exception e) {
			logger.error("Error retrieving challenges {}", e);
			return challenges;
		}
		logger.debug("Retrieved challenges {}", challenges);
		return challenges;
	}

	public long getChallengesCount() {
		long count = 0;
		try {
			count =  this.collection.countDocuments();
		} catch (Exception e) {
			logger.error("Error getting count of challenges {}", e);
			return count;
		}
		logger.debug("Got challenges count: {}", count);
		return count;
	}

	public boolean isPresent(String challengeName) {
		boolean isPresent = false;
		try {
			isPresent = this.collection.countDocuments(eq("name", challengeName)) > 0 ? true: false;
		} catch (Exception e) {
			logger.error("Error checking database {}", e);
			return isPresent;
		}
		logger.debug("challenge: {} isPresent: {}", challengeName, isPresent);
		return isPresent;
	}

	public boolean arePresent(List<String> challengeNames) {
		boolean arePresent = false;
		try {
			arePresent = this.collection.countDocuments(in("name", challengeNames)) > 0 ? true: false;
		} catch (Exception e) {
			logger.error("Error checking database [arePresent] {}", e);
			return arePresent;
		}
		logger.debug("challenges: {} arePresnt: {}", challengeNames, arePresent);
		return  arePresent;
	}

	public void deleteAll() {
		try {
			this.collection.deleteMany(new Document());
		} catch (Exception e) {
			logger.error("Error deleting challenges {}", e);
			return;
		}
		logger.debug("Deleted challenges successfully");
	}

}
