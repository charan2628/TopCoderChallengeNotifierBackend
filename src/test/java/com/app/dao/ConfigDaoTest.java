package com.app.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.app.AppRunner;
import com.app.model.Config;
import com.mongodb.MongoClient;

@SpringBootTest(classes = AppRunner.class)
public class ConfigDaoTest extends AbstractTestNGSpringContextTests{

	@Autowired 
	private MongoClient client;

	@Autowired
	private ConfigDao configDao;

	@Test
	public void configInsertionTest() {
		Assert.assertNotNull(this.client);
		Config config = new Config(Arrays.asList(new String[] {"i@i.com"}), Arrays.asList(new String[] {"nodejs"}));
		this.configDao.addConfig(config);
		Config configFromDb = this.configDao.getConfig();
		Assert.assertEquals(configFromDb.getEmails(), config.getEmails());
		Assert.assertEquals(configFromDb.getTags(), config.getTags());
	}

	@Test
	public void configTagsUpdateTest() {
		Assert.assertNotNull(this.client);

		List<String> emails = new ArrayList<>(); emails.add("i@i.com");
		List<String> tags = new ArrayList<>(); tags.add("nodejs");

		Config config = new Config(emails, tags);
		this.configDao.addConfig(config);
		tags.add("angular");
		this.configDao.addTags(Arrays.asList(new String[] {"angular"}));
		Config configFromDb = this.configDao.getConfig();
		Assert.assertEquals(configFromDb.getTags(), tags);
	}

	@Test
	public void configEmailsUpdateTest() {
		Assert.assertNotNull(this.client);

		List<String> emails = new ArrayList<>(); emails.add("i@i.com");
		List<String> tags = new ArrayList<>(); tags.add("nodejs");

		Config config = new Config(emails, tags);
		this.configDao.addConfig(config);
		emails.add("j@j.com");
		this.configDao.addEmails(Arrays.asList(new String[] {"j@j.com"}));
		Config configFromDb = this.configDao.getConfig();
		Assert.assertEquals(configFromDb.getEmails(), emails);
	}
}
