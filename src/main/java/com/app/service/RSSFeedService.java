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

/**
 * Gets the RSS Feed from the feedUrl and
 * maps it to Feed Object
 * 
 * @author charan2628
 *
 */
@Service
public class RSSFeedService {

	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	private ConfigService configService;
	private RestTemplate restTemplate;
	private String feedUrl;
	
	/**
	 * 
	 * @param restTemplateBuilder
	 * @param configService
	 * @param feedUrl
	 */
	public RSSFeedService(
			@Autowired RestTemplateBuilder restTemplateBuilder,
			@Autowired ConfigService configService,
			@Value("${feed.url}") String feedUrl) {
		
		this.restTemplate = restTemplateBuilder
				.build();
		this.configService = configService;
		this.feedUrl = feedUrl;
	}
	
	/**
	 * Makes a HTTP call for RSS Feed using the
	 * feed URL and maps to Feed object
	 * 
	 * if error then logs and returns null, 
	 * which will be handled by the caller
	 * 
	 * @return Feed
	 */
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
	/**
	 * A Helper method to get all Items
	 * from Feed got from getFeed() method
	 * 
	 * @return All Items
	 */
	public List<Item> getAllItems() {
		return this.getFeed().getChannel().getItems();
	}
	
	/**
	 * Filters the items from feed that match
	 * tags (ex: Node.Js, Java) got from ConfigService
	 * 
	 * @return filtered Items
	 */
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
	
	/**
	 * Helper method to create a regex pattern including
	 * all the tags 
	 * 
	 * Example:
	 * For tags (Node.Js, Java) it will create following regex pattern
	 * (?:Node\.Js|Java)
	 * 
	 * @param tags
	 * @return compiled pattern from tags
	 */
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
