package com.app.dao;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
			@Autowired MongoClient mongoClient, 
			@Value("${app.database}") String databaseName,
			@Value("${app.collection.challenges}") String challenges) {

		this.collection = mongoClient.getDatabase(databaseName).getCollection(challenges, Challenge.class);
		logger.debug("ChallengeDao initialized successfully DB: {} COLLECTION: {}", databaseName, challenges);
	}

	public void addchallenge(Challenge challenge) {
		this.collection.insertOne(challenge);
	}

	public void addChallenges(List<Challenge> challenges) {
		this.collection.insertMany(challenges);
	}

	public List<Challenge> getChallenges(String challengeName) {
		List<Challenge> challenges = new ArrayList<>();
		this.collection.find(eq("name", challengeName))
			.forEach((Consumer<Challenge>)(challenge) -> challenges.add(challenge));
		return challenges;
	}

	public List<Challenge> getchallenges() {
		List<Challenge> challenges = new ArrayList<>();
		this.collection.find()
			.forEach((Consumer<Challenge>)(challenge) -> challenges.add(challenge));
		return challenges;
	}

	public long getChallengesCount() {
		return this.collection.countDocuments();
	}

	public boolean isPresent(String challengeName) {
		return this.collection.countDocuments(eq("name", challengeName)) > 0 ? true: false;
	}

	public boolean arePresent(List<String> challengeNames) {
		return this.collection.countDocuments(in("name", challengeNames)) > 0 ? true: false;
	}

	public void deleteAll() {
		this.collection.deleteMany(new Document());
	}

}
