package com.app.notifier;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.app.model.Challenge;
import com.app.model.UserConfig;
import com.app.model.rss.Item;
import com.app.service.MailService;
import com.app.service.StatusService;
import com.app.service.UserConfigService;
import com.app.service.MailService.Mail;
import com.app.util.AppUtil;
import com.app.util.MailSubject;

/**
 * Notified new / all challenges.
 *
 * @author charan2628
 *
 */
@Component
public class ChallengeNotifier {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private StatusService statusService;
    private MailService mailService;
    private UserConfigService userConfigService;

    public ChallengeNotifier(
            MailService mailService, StatusService statusService,
            UserConfigService userConfigService) {

        this.mailService = mailService;
        this.statusService = statusService;
        this.userConfigService = userConfigService;
    }

    /**
     * Notifies challenges to users through
     * mails.
     *
     * @param emails 
     *        users email's
     * @param challenges 
     *        challenges to be sent
     */
    public void notifiyChallenges(
            List<String> emails,  List<Item> items) {

        List<Challenge> newChallenges;
        UserConfig userConfig;
        for(String email: emails) {
            userConfig = this.userConfigService.getUserConfig(email);
            List<Challenge> challenges = AppUtil.toChallenges(items, userConfig.getTags());
            newChallenges = this.newChallenges(challenges, userConfig.getNotifiedChallenges());
            Mail mail = this.mailService.challengesMessage(newChallenges);
            if(!mail.send(MailSubject.TOPCODER_CHALLENGE_NOTIFICATION, email)) {
                LOGGER.info("Error sending mail {}", email);
                this.statusService.error();
            } else {
                this.userConfigService.addChallenges(email, newChallenges);
            }
        }
    }

    /**
     * Helper method to get new challenges by filtering out old challenges.
     *
     * The method uses HashMap's compute method to
     * efficiently do set subtraction
     *
     * @param challenges list of challenges to send
     * @param names old challenge names sent
     * @return challenges not sent yet
     */
    private List<Challenge> newChallenges(
            List<Challenge> challenges,
            List<String> names) {
        Map<String, Challenge> newChallenges = new HashMap<>();
        challenges
            .forEach(challenge -> newChallenges.put(challenge.getName(), challenge));
        names.forEach(name -> {
            newChallenges.compute(name, (n, challenge) -> null);
        });
        return new ArrayList<>(newChallenges.values());
    }
}
