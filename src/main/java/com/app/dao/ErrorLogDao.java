package com.app.dao;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.app.model.ErrorMessage;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

@Repository
public class ErrorLogDao {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private MongoCollection<ErrorMessage> collection;

    public ErrorLogDao(
            @Autowired MongoClient mongoClient, 
            @Value("${app.db}") String databaseName,
            @Value("${app.db.coll.errorlog}") String challenges) {
        
        this.collection = mongoClient.getDatabase(databaseName).getCollection(challenges, ErrorMessage.class);
        LOGGER.debug("ErrorLogDao initialized successfully DB: {} COLLECTION: {}", databaseName, challenges);
    }
    
    public void addErrorLog(String error) {
        this.collection.insertOne(
                new ErrorMessage(error));
    }
    
    public List<String> getErrorLogs() {
        List<String> errors = new ArrayList<>();
        this.collection.find()
            .forEach((Consumer<ErrorMessage>)(error) -> errors.add(error.getMsg()));
        return errors;
    }
}
