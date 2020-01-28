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

@Service
public class ConfigService {
	
	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private ConfigDao configDao;
	
	public Config getConfig() {
		Config config = null;
		try {
			config = this.configDao.getConfig();
		} catch (Exception e) {
			logger.error("Error retrieving config {}", e);
			return config;
		}
		logger.debug("Got Config {}", config);
		return config;
	}
	
	public void addConfig(Config config) {
		try {
			config.setEmails(AppUtil.removeDups(config.getEmails()));
			config.setTags(AppUtil.removeDups(config.getTags()));
			this.configDao.addConfig(config);
		} catch (Exception e) {
			logger.error("Error adding config: {} {}", config, e);
			return;
		}
		logger.debug("Added config: {} successfully", config);
	}
	
	public void updateTags(List<String> tags) {
		try {
			Config config = this.getConfig();
			if(config == null) {
				config = new Config(new ArrayList<>(), AppUtil.removeDups(tags));
			} else {
				config.getTags().addAll(tags);
				config.setTags(AppUtil.removeDups(config.getTags()));
			}
			config.setId(null);
			this.addConfig(config);
		} catch (Exception e) {
			logger.error("Error updating tags: {} {}", tags, e);
			return;
		}
		logger.debug("Updated tags: {} successfully", tags);
	}
	
	public void updateEmails(List<String> emails) {
		try {
			Config config = this.getConfig();
			if(config == null) {
				config = new Config(AppUtil.removeDups(emails), new ArrayList<>());
			} else {
				config.getEmails().addAll(emails);
				config.setEmails(AppUtil.removeDups(config.getEmails()));
			}
			config.setId(null);
			this.addConfig(config);
		} catch (Exception e) {
			logger.error("Error updating emails: {} {}", emails, e);
			return;
		}
		logger.debug("Updated emails: {} successfully", emails);
	}
	
	public void deleteEmails(List<String> emails) {
		try {
			Config config = this.getConfig();
			if(config == null) return;
			
			List<String> prevMails = config.getEmails();
			Map<String, Object> filteredOutMails = new HashMap<>();
			prevMails.forEach(mail -> filteredOutMails.put(mail, null));
			emails.forEach(mail -> {
				filteredOutMails.compute(mail, (k, v) -> null);
			});
			
			config.setEmails(new ArrayList<>(filteredOutMails.keySet()));
			config.setId(null);
			this.addConfig(config);
		} catch (Exception e) {
			logger.error("Error deleting mails: {} {}", emails, e);
			return;
		}
		logger.debug("Deleted Emails: {} successfully", emails);
	}
	
	public void deleteTags(List<String> tags) {
		try {
			Config config = this.getConfig();
			if(config == null) return;
			
			List<String> prevTags = config.getTags();
			Map<String, Object> filteredOutTags = new HashMap<>();
			prevTags.forEach(tag -> filteredOutTags.put(tag, null));
			tags.forEach(tag -> {
				filteredOutTags.compute(tag, (k, v) -> null);
			});
			
			config.setTags(new ArrayList<>(filteredOutTags.keySet()));
			config.setId(null);
			this.addConfig(config);
		} catch (Exception e) {
			logger.error("Error deleting tags: {} {}", tags, e);
			return;
		}
		logger.debug("Deleted tags: {} successfully", tags);
	}
}
