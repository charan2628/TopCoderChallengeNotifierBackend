package com.app.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;

import com.app.AppRunner;
import com.app.model.Challenge;
import com.app.model.Config;
import com.app.model.rss.Item;

@ActiveProfiles("test")
@SpringBootTest(classes = AppRunner.class, webEnvironment = WebEnvironment.NONE)
public class MailServiceCheck extends AbstractTestNGSpringContextTests{
	
	@Autowired
	private Environment environment;
	private ConfigService configService;
	
	@BeforeTest
	public void setUp() {
		List<String> emails = new ArrayList<>(); emails.add("s.charancherry22@gmail.com");
		Config config = new Config(emails, null);
		this.configService = mock(ConfigService.class);
		when(this.configService.getConfig()).thenReturn(config);
	}

	@Test
	public void mailSendCheck() throws IOException{
		List<Item> items = RSSFeedTestData.items();
		List<Challenge> challenges = new ArrayList<>();
		challenges.add(new Challenge(items.get(0).getTitle(), items.get(0).getDescription(), items.get(0).getLink()));
		challenges.add(new Challenge(items.get(2).getTitle(), items.get(2).getDescription(), items.get(2).getLink()));
		MailService mailService = new MailService(configService, environment);
		mailService.buildMessage(challenges).send();
	}
}
