package com.app.service;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.app.model.Challenge;
import com.app.model.Config;
import com.app.util.MailHTMLMessageBuilder;

/**
 * Service class to send mail of
 * new Challenges.
 *
 * @author charan2628
 *
 */
@Service
public class MailService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private StatusService statusService;
    @Autowired
    private ErrorLogService errorLogService;
    private ConfigService configService;
    private Properties properties;
    private Environment environment;

    /**
     * Constructor to build MailService constructor injection.
     *
     * <p> Constructor Injection to ease testing
     */
    public MailService(
            @Autowired ConfigService configService,
            @Autowired Environment environment) {

        this.configService = configService;
        this.properties = new Properties();
        this.configProperties();
        this.environment = environment;
    }

    private void configProperties() {
        this.properties.put("mail.smtp.host", "smtp.gmail.com");
        this.properties.put("mail.smtp.socketFactory.port", "465");
        this.properties.put(
                "mail.smtp.socketFactory.class",  
                "javax.net.ssl.SSLSocketFactory");
        this.properties.put("mail.smtp.auth", "true");
        this.properties.put("mail.smtp.port", "465");
    }

    /**
     * Builds message using MailHTMLMessageBuilder,
     * which use velocity to create message from templates
     * and return Mail which is ready to send.
     *
     * @param challenges
     * @return Mail ready to send
     */
    public Mail buildMessage(List<Challenge> challenges) {
        String message = MailHTMLMessageBuilder.build(challenges);
        return new Mail(message);
    }

    /**
     * Mail encapsulated with message to be sent
     *
     * @author charan2628
     *
     */
    public class Mail {

        private String body;

        private Mail(String body) {
            this.body = body;
        }

        /**
         * Sends the mail to list of mails got from
         * ConfigService if any error sending mail logs it and
         * continue to next mail.
         *
         */
        public void send() {
            Session session = Session
                    .getDefaultInstance(
                            MailService.this.properties,
                            new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(
                                MailService.this.environment
                                    .getProperty("APP_SENDER_MAIL"),
                                MailService.this.environment
                                    .getProperty("APP_SENDER_MAIL_PASSWORD"));
                                }
                              });
            Config config = MailService.this.configService.getConfig();
            for (String mail: config.getEmails()) {
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
                    message.setSubject("TopCoder Challenges check out");
                    message.setDataHandler(new DataHandler(
                            new ByteArrayDataSource(this.body, "text/html")));
                    Transport.send(message);
                } catch (MessagingException | IOException exception) {
                    LOGGER.error("Error sending mail {} {}", mail, exception);
                    MailService.this.errorLogService.addErrorLog(
                            String.format("Error sending mail %s",
                                    LocalDateTime.now().toString()));
                    MailService.this.statusService.error();
                    continue;
                }
                LOGGER.debug("Mail to: {} sent successfully", mail);
                    }
                }
            }
}
