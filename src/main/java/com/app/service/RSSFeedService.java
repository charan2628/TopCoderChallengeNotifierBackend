package com.app.service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.model.Config;
import com.app.model.rss.Feed;
import com.app.model.rss.Item;

@Service
public class RSSFeedService {

	private ConfigService configService;
	private RestTemplate restTemplate;
	
	public RSSFeedService(
			@Autowired RestTemplateBuilder restTemplateBuilder,
			@Autowired ConfigService configService) {
		this.restTemplate = restTemplateBuilder
				.build();
		this.configService = configService;
	}
	
	public Feed getFeed() {
		return this.restTemplate.getForObject("http://feeds.topcoder.com/challenges/feed?list=active&contestType=develop&bucket=openForRegistration", Feed.class);
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
