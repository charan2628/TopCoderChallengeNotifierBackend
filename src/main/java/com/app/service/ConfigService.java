package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ConfigDao;
import com.app.model.Config;

@Service
public class ConfigService {

	@Autowired
	private ConfigDao configDao;
	
	public Config getConfig() {
		return this.configDao.getConfig();
	}
	
	public void addConfig(Config config) {
		this.configDao.addConfig(config);
	}
	
	public void updateTags(List<String> tags) {
		this.configDao.addTags(tags);
	}
	
	public void updateEmails(List<String> emails) {
		this.configDao.addEmails(emails);
	}
}
