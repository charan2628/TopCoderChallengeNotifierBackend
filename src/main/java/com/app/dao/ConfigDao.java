package com.app.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.app.model.Config;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import java.lang.invoke.MethodHandles;

//To the future me this is a collection capped to one document
@Repository
public class ConfigDao {

	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private MongoCollection<Config> collection;

	public ConfigDao(
			MongoClient mongoClient, 
			@Value("${app.database}") String databaseName,
			@Value("${app.collection.config}") String challenges) {
		this.collection = mongoClient.getDatabase(databaseName).getCollection(challenges, Config.class);
		logger.debug("ConfigDao initialized successfully DB: {} COLLECTION: {}", databaseName, challenges);
	}

	public Config getConfig() {
		return this.collection.find().first();
	}

	public void addConfig(Config config) {
		this.collection.insertOne(config);
	}

}
