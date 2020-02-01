package com.app.dao;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.app.model.Status;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

@Repository
public class StatusDao {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private MongoCollection<Status> collection;

    public StatusDao(
            @Autowired MongoClient mongoClient, 
            @Value("${app.db}") String databaseName,
            @Value("${app.db.coll.status}") String challenges) {
        
        this.collection = mongoClient.getDatabase(databaseName).getCollection(challenges, Status.class);
        LOGGER.debug("StatusDao initialized successfully DB: {} COLLECTION: {}", databaseName, challenges);
    }

    public Status getStatus() {
        return this.collection.find().first();
    }

    public void addStatus(Status status) {
        this.collection.insertOne(status);
    }
}
