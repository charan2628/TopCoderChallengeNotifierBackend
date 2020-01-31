package com.app.service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.model.Config;
import com.app.model.rss.Feed;
import com.app.model.rss.Item;


@Service
public class RSSFeedService {

	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	private ConfigService configService;
	private RestTemplate restTemplate;
	private String feedUrl;
	
	public RSSFeedService(
			@Autowired RestTemplateBuilder restTemplateBuilder,
			@Autowired ConfigService configService,
			@Value("${feed.url}") String feedUrl) {
		
		this.restTemplate = restTemplateBuilder
				.build();
		this.configService = configService;
		this.feedUrl = feedUrl;
	}
	
	public Feed getFeed() {
		Feed feed = null;
		try {
			feed = this.restTemplate.getForObject(this.feedUrl, Feed.class);
		} catch (Exception e) {
			logger.error("Error retrieving feed {}", e);
			return feed;
		}
		logger.debug("Got Feed: {}", feed);
		return feed;
		
	}
	
	public List<Item> getAllItems() {
		return this.getFeed().getChannel().getItems();
	}
	
	public List<Item> getItems() {
		Config config = this.configService.getConfig();
		final Pattern pattern = this.createPattern(config.getTags());
		List<Item> items = this.getAllItems()
				.stream()
				.filter((Item item) -> {
					String description = item.getDescription();
					if(pattern.matcher(description).find()) {
						return true;
					}
					return false;
				})
				.collect(Collectors.toList());
		return items;
	}
	
	private Pattern createPattern(List<String> tags) {
		StringBuilder regex = new StringBuilder();
		regex.append("(?:");
		if(tags.size() > 0) {
			regex.append(tags.get(0));
		}
		for(int i = 1; i < tags.size(); i++) {
			regex.append(String.format("|%s", tags.get(i)));
		}
		regex.append(")");
		return Pattern.compile(regex.toString());
	}
}
