package com.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.app.AppRunner;
import com.app.model.Config;

@ActiveProfiles("test")
@SpringBootTest(classes = AppRunner.class)
public class ConfigServiceTest extends AbstractTestNGSpringContextTests{

	@Autowired
	private ConfigService configService;

	@Test
	public void configInsertionTest() {
		Config config = new Config(Arrays.asList(new String[] {"i@i.com"}), Arrays.asList(new String[] {"nodejs"}));
		this.configService.addConfig(config);
		Config configFromDb = this.configService.getConfig();
		Assert.assertEquals(configFromDb.getEmails(), config.getEmails());
		Assert.assertEquals(configFromDb.getTags(), config.getTags());
	}

	@Test
	public void configTagsUpdateTest() {
		List<String> emails = new ArrayList<>(); emails.add("i@i.com");
		List<String> tags = new ArrayList<>(); tags.add("nodejs");

		Config config = new Config(emails, tags);
		this.configService.addConfig(config);
		tags.add("angular");
		this.configService.updateTags(Arrays.asList(new String[] {"angular"}));
		Config configFromDb = this.configService.getConfig();
		Assert.assertEqualsNoOrder(configFromDb.getTags().toArray(), tags.toArray());
	}

	@Test
	public void configEmailsUpdateTest() {
		List<String> emails = new ArrayList<>(); emails.add("i@i.com");
		List<String> tags = new ArrayList<>(); tags.add("nodejs");

		Config config = new Config(emails, tags);
		this.configService.addConfig(config);
		emails.add("j@j.com");
		this.configService.updateEmails(Arrays.asList(new String[] {"j@j.com"}));
		Config configFromDb = this.configService.getConfig();
		Assert.assertEqualsNoOrder(configFromDb.getEmails().toArray(), emails.toArray());
	}
}
