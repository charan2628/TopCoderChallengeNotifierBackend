package com.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.app.model.Challenge;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;;

@Repository
public class ChallengeDao {
	
	private MongoCollection<Challenge> collection;
	
	public ChallengeDao(
			MongoClient mongoClient, 
			@Value("${app.database}") String databaseName,
			@Value("${app.collection.challenges}") String challenges) {
		this.collection = mongoClient.getDatabase(databaseName).getCollection(challenges, Challenge.class);
	}
	
	public void addchallenge(Challenge challenge) {
		this.collection.insertOne(challenge);
	}

	public FindIterable<Challenge> getChallenges(String challengeName) {
		return this.collection.find(eq("name", challengeName));
	}
	
	public FindIterable<Challenge> getchallenges() {
		return this.collection.find();
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
	
//	public void deleteAll() {
//		this.collection.deleteMany(new Document());
//	}
	
}
