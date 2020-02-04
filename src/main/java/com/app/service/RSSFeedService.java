package com.app.service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.model.rss.Feed;
import com.app.model.rss.Item;

/**
 * Gets the RSS Feed from the feedUrl and
 * maps it to Feed Object.
 *
 * @author charan2628
 *
 */
@Service
public class RSSFeedService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());


    @Autowired
    private StatusService statusService;
    @Autowired
    private ErrorLogService errorLogService;
    private RestTemplate restTemplate;
    private String feedUrl;

    /**
     * Constructor to build RSSFeedService constructor injection.
     *
     * <p> Constructor Injection to ease testing
     */
    public RSSFeedService(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${feed.url}") String feedUrl) {

        this.restTemplate = restTemplateBuilder
                .build();
        this.feedUrl = feedUrl;
    }

    /**
     * Makes a HTTP call for RSS Feed using the
     * feed URL and maps to Feed object.
     *
     *<p> if error then logs and returns null,
     * which will be handled by the caller
     *
     * @return Feed
     */
    public Feed getFeed() {
        Feed feed = null;
        try {
            feed = this.restTemplate.getForObject(this.feedUrl, Feed.class);
        } catch (Exception e) {
            LOGGER.error("Error retrieving feed {}", e);
            this.errorLogService.addErrorLog(
                    String.format("Error retrieving feed %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            return feed;
        }
        LOGGER.debug("Got Feed: {}", feed);
        return feed;
    }

    /**
     * A Helper method to get all Items
     * from Feed got from getFeed() method.
     *
     * @return All Items
     */
    public List<Item> getItems() {
        return this.getFeed().getChannel().getItems();
    }
}
