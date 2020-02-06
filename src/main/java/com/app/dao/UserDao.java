package com.app.dao;

import java.lang.invoke.MethodHandles;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.app.model.User;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

@Repository
public class UserDao {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private MongoCollection<User> collection;

    public UserDao(
            @Autowired MongoClient mongoClient, 
            @Value("${app.db}") String databaseName,
            @Value("${app.db.coll.user}") String challenges) {
        
        this.collection = mongoClient.getDatabase(databaseName).getCollection(challenges, User.class);
        LOGGER.debug("UserDao initialized successfully DB: {} COLLECTION: {}", databaseName, challenges);
    }

    public User getUser(String email) {
        return this.collection.find(eq("email", email)).first();
    }

    public void addUser(User user) {
        this.collection.insertOne(user);
    }

    public void confirmUser(ObjectId id) {
        this.collection.updateOne(eq("_id", id), combine(set("confirmed", true)));
    }
    
    public boolean isUserPresent(String email) {
        return this.collection.find(eq("email", email)).first() == null ? false : true;
    }

    public void _deleteAll() {
        this.collection.deleteMany(new Document());
    }
}
