package com.app.util;

import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.app.model.Challenge;

/**
 * Utility class for building HTML messages.
 *
 * @author charan2628
 *
 */
public class MailHTMLMessageBuilder {

    static {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty(
        "class.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(properties);
    }

    /**
     * Private constructor.
     */
    private MailHTMLMessageBuilder() { }

    /**
     * Builds HTML Template using velocity template provided as
     * templates/mail_html_message.vm.
     *
     * <p> Right now it's hard coded.
     *
     * @param challenges
     *        list of challenges to build HTML message for
     * @return HTML message build using the given challenges and template
     */
    public static String build(final List<Challenge> challenges) {
        VelocityContext context = new VelocityContext();
        context.put("challenges", challenges);
        Template template = Velocity
                .getTemplate("templates/mail_html_message.vm");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }
}
