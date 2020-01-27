package com.app.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.AppRunner;
import com.app.model.Config;
import com.app.model.rss.Feed;
import com.app.model.rss.Item;

@ActiveProfiles("test")
@SpringBootTest(classes = AppRunner.class)
public class RSSFeedServiceTest extends AbstractTestNGSpringContextTests{

	private ConfigService configService;
	private RSSFeedService rssFeedService;
	
	@BeforeTest
	public void setUp() {
		RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
		RestTemplate restTemplate = mock(RestTemplate.class);
		when(restTemplateBuilder.build()).thenReturn(restTemplate);
		when(restTemplate
				.getForObject(
						"http://feeds.topcoder.com/challenges/feed?list=active&contestType=develop&bucket=openForRegistration", 
						Feed.class)
				)
		.thenReturn(RSSFeedTestData.feed());
		this.configService = mock(ConfigService.class);
		this.rssFeedService = new RSSFeedService(restTemplateBuilder, this.configService);
	}
	
	@Test
	public void rssFeedTest() throws IOException {
		Assert.assertNotNull(this.rssFeedService.getFeed());
	}
	
	@Test
	public void itemsByTagsTest() {
		List<String> tags = new ArrayList<>(); tags.add("Node.js"); tags.add("AWS");
		Config config = new Config(); config.setTags(tags);
		when(this.configService.getConfig())
			.thenReturn(config);
		List<Item> items = this.rssFeedService.getItems();
		Assert.assertEquals(items.size(), 5);
	}
	
	@Test
	public void itemsByTagsTest2() {
		List<String> tags = new ArrayList<>(); tags.add("Apache Cordova"); tags.add("Apache Kafka");
		Config config = new Config(); config.setTags(tags);
		when(this.configService.getConfig())
			.thenReturn(config);
		List<Item> items = this.rssFeedService.getItems();
		Assert.assertEquals(items.size(), 3);
	}
	
}
