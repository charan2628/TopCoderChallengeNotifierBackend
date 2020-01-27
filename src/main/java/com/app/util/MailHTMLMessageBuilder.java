package com.app.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.app.model.Challenge;

public class MailHTMLMessageBuilder {
	
	static {
		Properties properties = new Properties();
		properties.setProperty("resource.loader", "class");
		properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(properties);
	}

	public static String build(List<Challenge> challenges) throws IOException {
		VelocityContext context = new VelocityContext();
		context.put("challenges", challenges);
		Template template = Velocity.getTemplate("templates/mail_html_message.vm");
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();
	}
}
