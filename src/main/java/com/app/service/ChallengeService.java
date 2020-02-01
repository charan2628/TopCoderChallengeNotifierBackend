package com.app.service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ChallengeDao;
import com.app.model.Challenge;

/**
 * Service class to get and add challenges
 *
 * @author charan2628
 *
 */
@Service
public class ChallengeService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private StatusService statusService;
    @Autowired
    private ErrorLogService errorLogService;
    private ChallengeDao challengeDao;

    /**
     * Constructor to build ChallengeService constructor injection.
     *
     * <p> Constructor Injection to ease testing
     */
    public ChallengeService(@Autowired ChallengeDao challengeDao) {
        this.challengeDao = challengeDao;
    }

    /**
     * Gets a list of challenges that are saved in db
     * means already notified through mail
     *
     * If error logs it and throws it which will be handled by
     * caller
     *
     * @return list of notified challenges
     */
    public List<Challenge> getChallenges() {
        List<Challenge> challenges = new ArrayList<>();
        try {
            challenges = this.challengeDao.getchallenges();
        } catch (Exception exception) {
            LOGGER.error("Error retrieving challenges {}", exception);
            this.errorLogService.addErrorLog(
                    String.format("Error retrieving challenges %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw exception;
        }
        LOGGER.debug("Retrieved challenges {}", challenges);
        return challenges;
    }

    /**
     * Gets a list of challenges by name that are saved in db
     * means already notified through mail
     *
     * If error logs it and throws it which will be handled by
     * caller
     *
     * @return list of notified challenges that match the given name
     */
    public List<Challenge> getChallenges(String challengeName) {
        List<Challenge> challenges = new ArrayList<>();
        try {
            challenges = this.challengeDao.getChallenges(challengeName);
        } catch (Exception exception) {
            LOGGER.error("Error retrieving challenges name: {} {}",
                    challengeName, exception);
            this.errorLogService.addErrorLog(
                    String.format("Error retrieving challenges by name %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw exception;
        }
        LOGGER.debug("Retrieved challenges: {} name: {}", challenges, challengeName);
        return challenges;
    }

    /**
     * Adds challenge to db
     *
     * If error logs it and throws it which will be handled by
     * caller
     *
     * @param challenge
     */
    public void addChallenge(Challenge challenge) {
        try {
            this.challengeDao.addchallenge(challenge);
        } catch (Exception exception) {
            LOGGER.error("Error inserting challenge {} {}",
                    challenge, exception);
            this.errorLogService.addErrorLog(
                    String.format("Error adding challenge %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw exception;
        }
        LOGGER.debug("Added challenge: {} successfully", challenge);
    }

    /**
     * Adds list of challenges to db
     *
     * If error logs it and throws it which will be handled by
     * caller
     *
     * @param challenges
     */
    public void addChallenges(List<Challenge> challenges) {
        try {
            this.challengeDao.addChallenges(challenges);
        } catch (Exception exception) {
            LOGGER.error("Error inserting challenges: {} {}",
                    challenges, exception);
            this.errorLogService.addErrorLog(
                    String.format("Error adding challenges %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw exception;
        }
        LOGGER.debug("Added challenges: {} successfully", challenges);
    }

    /**
     * Gets a count of notified challenges
     *
     * If error logs it and throws it which will be handled by
     * caller
     *
     * @return challenge count
     */
    public long getChallengesCount() {
        long count = 0;
        try {
            count =  this.challengeDao.getChallengesCount();
        } catch (Exception exception) {
            LOGGER.error("Error getting count of challenges {}", exception);
            this.errorLogService.addErrorLog(
                    String.format("Error retrieving count of challenges %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw exception;
        }
        LOGGER.debug("Got challenges count: {}", count);
        return count;

    }

    /**
     * Checks if challenge of given name is
     * present in db
     *
     * If error logs it and throws it which will be handled by
     * caller
     *
     * @param challengeName
     * @return is challenge present
     */
    public boolean isPresent(String challengeName) {
        boolean isPresent = false;
        try {
            isPresent = this.challengeDao.isPresent(challengeName);
        } catch (Exception exception) {
            LOGGER.error("Error checking database {}", exception);
            this.errorLogService.addErrorLog(
                    String.format("Error checking databse for challenges %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw exception;
        }
        LOGGER.debug("challenge: {} isPresent: {}", challengeName, isPresent);
        return isPresent;
    }

    /**
     * Checks if given list of challenges are
     * present in db
     *
     * If error logs it and throws it which will be handled by
     * caller
     *
     * @param challengeNames
     * @return
     */
    public boolean arePresent(List<String> challengeNames) {
        boolean arePresent = false;
        try {
            arePresent = this.challengeDao.arePresent(challengeNames);
        } catch (Exception exception) {
            LOGGER.error("Error checking database [arePresent] {}", exception);
            this.errorLogService.addErrorLog(
                    String.format("Error checking databse for challenges %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw exception;
        }
        LOGGER.debug("challenges: {} arePresnt: {}", challengeNames, arePresent);
        return  arePresent;
    }

    /**
     * Deletes all challenges in  db (Handle with care)
     *
     * If error logs it and throws it which will be handled by
     * caller
     */
    public void deleteAll() {
        try {
            this.challengeDao.deleteAll();
        } catch (Exception exception) {
            LOGGER.error("Error deleting challenges {}", exception);
            this.errorLogService.addErrorLog(
                    String.format("Error deleting challenges %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw exception;
        }
        LOGGER.debug("Deleted challenges successfully");
    }
}
