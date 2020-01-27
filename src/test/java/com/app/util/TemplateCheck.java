package com.app.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.testng.annotations.Test;

import com.app.model.Challenge;
import com.app.model.rss.Item;
import com.app.service.RSSFeedTestData;

public class TemplateCheck {

	@Test
	public void check() throws IOException {
		Properties properties = new Properties();
		properties.setProperty("resource.loader", "class");
		properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(properties);
		VelocityContext context = new VelocityContext();
		List<Item> items = RSSFeedTestData.items();
		List<Challenge> challenges = new ArrayList<>();
		challenges.add(new Challenge(items.get(0).getTitle(), items.get(0).getDescription(), items.get(0).getLink()));
		context.put("challenges", challenges);
		Template template = Velocity.getTemplate("templates/mail_html_message.vm");
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		System.out.println(writer.toString());
	}
}
