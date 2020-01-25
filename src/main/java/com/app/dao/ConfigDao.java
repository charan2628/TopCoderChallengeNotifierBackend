package com.app.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.app.model.Config;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import java.util.List;

//To the future me this is a collection capped to one document
@Repository
public class ConfigDao {
private MongoCollection<Config> collection;
	
	public ConfigDao(
			MongoClient mongoClient, 
			@Value("${app.database}") String databaseName,
			@Value("${app.collection.config}") String challenges) {
		this.collection = mongoClient.getDatabase(databaseName).getCollection(challenges, Config.class);
	}
	
	public Config getConfig() {
		return this.collection.find().first();
	}
	
	public void addConfig(Config config) {
		this.collection.insertOne(config);
	}
	
	public void addTags(List<String> tags) {
		Config config = this.getConfig();
		config.getTags().addAll(tags);
		config.setId(null);
		this.addConfig(config);
	}
	
	public void addEmails(List<String> emails) {
		Config config = this.getConfig();
		config.getEmails().addAll(emails);
		config.setId(null);
		this.addConfig(config);
	}
}
