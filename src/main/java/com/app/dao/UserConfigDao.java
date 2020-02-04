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

import com.app.model.UserConfig;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

@Repository
public class UserConfigDao {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private MongoCollection<UserConfig> collection;

    public UserConfigDao(
            @Autowired MongoClient mongoClient, 
            @Value("${app.db}") String databaseName,
            @Value("${app.db.coll.userconfig}") String challenges) {
        
        this.collection = mongoClient.getDatabase(databaseName).getCollection(challenges, UserConfig.class);
        LOGGER.debug("UserDao initialized successfully DB: {} COLLECTION: {}", databaseName, challenges);
    }

    public UserConfig getUserConfig(String email) {
        return this.collection.find(eq("email", email)).first();
    }

    public void addUserCOnfig(UserConfig userConfig) {
        this.collection.insertOne(userConfig);
    }

    public void updateTags(String email, List<String> tags) {
        this.collection.updateOne(eq("email", email), set("tags", tags));
    }

    public void scheduleTime(String email, long time) {
        this.collection.updateOne(eq("email", email), set("scheduleTime", time));
    }

    public void addChallenges(String email, List<String> challengeNames) {
        this.collection.updateOne(eq("email", email), pushEach("notifiedChallenges", challengeNames));
    }

    public List<UserConfig> withinTime(long minTime, long maxTime) {
        List<UserConfig> userConfigs = new ArrayList<UserConfig>();
        this.collection.find(and(gte("scheduleTime", minTime), lt("scheduleTime", maxTime)))
            .forEach((Consumer<UserConfig>)(uc) -> userConfigs.add(uc));
        return userConfigs;
    }

    public void _deleteAll() {
        this.collection.deleteMany(new Document());
    }
}
