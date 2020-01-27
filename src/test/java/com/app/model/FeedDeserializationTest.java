package com.app.model;

import java.io.IOException;

import org.springframework.test.context.ActiveProfiles;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.app.model.rss.Feed;
import com.app.service.RSSFeedTestData;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@ActiveProfiles("test")
public class FeedDeserializationTest {

	
	@Test
	public void feedDeSerializationTest() throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		Feed actualFeed = xmlMapper.readValue(this.getClass().getClassLoader().getResourceAsStream("rss.xml"), Feed.class);
		Feed expectedFeed = RSSFeedTestData.feed();
		Assert.assertEquals(actualFeed.getChannel().getTitle(), expectedFeed.getChannel().getTitle());
		Assert.assertEquals(actualFeed.getChannel().getDescription(), expectedFeed.getChannel().getDescription());
//		Assert.assertEquals(actualFeed.getChannel().getLink(), expectedFeed.getChannel().getLink());
		Assert.assertEquals(actualFeed.getChannel().getGenerator(), expectedFeed.getChannel().getGenerator());
		Assert.assertEquals(actualFeed.getChannel().getLastBuildDate(), expectedFeed.getChannel().getLastBuildDate());
		Assert.assertEquals(actualFeed.getChannel().getAtomLink(), expectedFeed.getChannel().getAtomLink());
		Assert.assertEqualsNoOrder(actualFeed.getChannel().getItems().toArray(), expectedFeed.getChannel().getItems().toArray());
	}
	
	
}
