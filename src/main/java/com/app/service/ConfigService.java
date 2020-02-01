package com.app.service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ConfigDao;
import com.app.model.Config;
import com.app.util.AppUtil;

/**
 * Service class to get, add, update Config.
 *
 * @author charan2628
 *
 */
@Service
public class ConfigService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private ConfigDao configDao;

    /**
     * Constructor to build ConfigService constructor injection.
     *
     * <p> Constructor Injection to ease testing
     */
    public ConfigService(
            @Autowired ConfigDao configDao) {
        this.configDao = configDao;
    }

    /**
     * Gets the Config from db.
     *
     * <p>If error logs it and throws it which will be handled by
     * caller
     *
     * @return config from db
     */
    public Config getConfig() {
        Config config = null;
        try {
            config = this.configDao.getConfig();
        } catch (Exception exception) {
            LOGGER.error("Error retrieving config {}", exception);
            throw exception;
        }
        LOGGER.debug("Got Config {}", config);
        return config;
    }

    /**
     * Adds the Config to mongo db.
     *
     * <p>Since the collection is capped to one record, adding this
     * cause the complete replacing of previous Config.
     *
     * If error logs it and throws it which will be handled by
     * caller
     *
     * @param config
     */
    public void addConfig(Config config) {
        try {
            config.setEmails(AppUtil.removeDups(config.getEmails()));
            config.setTags(AppUtil.removeDups(config.getTags()));
            this.configDao.addConfig(config);
        } catch (Exception exception) {
            LOGGER.error("Error adding config: {} {}", config, exception);
            throw exception;
        }
        LOGGER.debug("Added config: {} successfully", config);
    }

    /**
     * Updates the tags of Config.
     *
     * <p>This method removes duplicate tags before adding it to db
     * Since, it's a capped collection update is not possible, so it does
     * whole replacement of record persisting previous values;
     *
     * If error logs it and throws it which will be handled by
     * caller
     *
     * @param tags to add
     */
    public void updateTags(List<String> tags) {
        try {
            Config config = this.getConfig();
            if (config == null) {
                config = new Config(new ArrayList<>(), AppUtil.removeDups(tags));
            } else {
                config.getTags().addAll(tags);
                config.setTags(AppUtil.removeDups(config.getTags()));
            }
            config.setId(null);
            this.addConfig(config);
        } catch (Exception exception) {
            LOGGER.error("Error updating tags: {} {}", tags, exception);
            throw exception;
        }
        LOGGER.debug("Updated tags: {} successfully", tags);
    }

    /**
     * Updates the email's of Config.
     *
     * <p>This method removes duplicate email's before adding it to db
     * Since, it's a capped collection update is not possible, so it does
     * whole replacement of record persisting previous values;
     *
     * If error logs it and throws it which will be handled by
     * caller
     *
     * @param emails to add
     */
    public void updateEmails(List<String> emails) {
        try {
            Config config = this.getConfig();
            if (config == null) {
                config = new Config(AppUtil.removeDups(emails), new ArrayList<>());
            } else {
                config.getEmails().addAll(emails);
                config.setEmails(AppUtil.removeDups(config.getEmails()));
            }
            config.setId(null);
            this.addConfig(config);
        } catch (Exception exception) {
            LOGGER.error("Error updating emails: {} {}", emails, exception);
            throw exception;
        }
        LOGGER.debug("Updated emails: {} successfully", emails);
    }

    /**
     * Deletes the email's of Config.
     *
     * The method uses HashMap's compute method to
     * efficiently do set subtraction
     *
     * If error logs it and throws it which will be handled by
     * caller
     *
     * @param emails to delete
     */
    public void deleteEmails(List<String> emails) {
        try {
            Config config = this.getConfig();
            if (config == null) {
                return;
            }

            List<String> prevMails = config.getEmails();
            Map<String, Object> filteredOutMails = new HashMap<>();
            prevMails.forEach(mail -> filteredOutMails.put(mail, null));
            emails.forEach(mail -> {
                filteredOutMails.compute(mail, (k, v) -> null);
            });

            config.setEmails(new ArrayList<>(filteredOutMails.keySet()));
            config.setId(null);
            this.addConfig(config);
        } catch (Exception exception) {
            LOGGER.error("Error deleting mails: {} {}", emails, exception);
            throw exception;
        }
        LOGGER.debug("Deleted Emails: {} successfully", emails);
    }

    /**
     * Deletes the tags of Config.
     *
     * <p>The method uses HashMap's compute method to
     * efficiently do set subtraction
     *
     * If error logs it and throws it which will be handled by
     * caller
     *
     * @param tags to delete
     */
    public void deleteTags(List<String> tags) {
        try {
            Config config = this.getConfig();
            if (config == null) {
                return;
            }

            List<String> prevTags = config.getTags();
            Map<String, Object> filteredOutTags = new HashMap<>();
            prevTags.forEach(tag -> filteredOutTags.put(tag, null));
            tags.forEach(tag -> {
                filteredOutTags.compute(tag, (k, v) -> null);
            });

            config.setTags(new ArrayList<>(filteredOutTags.keySet()));
            config.setId(null);
            this.addConfig(config);
        } catch (Exception exception) {
            LOGGER.error("Error deleting tags: {} {}", tags, exception);
            throw exception;
        }
        LOGGER.debug("Deleted tags: {} successfully", tags);
    }
}
