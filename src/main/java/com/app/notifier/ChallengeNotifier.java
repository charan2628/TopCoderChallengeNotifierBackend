package com.app.notifier;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.model.Challenge;
import com.app.model.rss.Item;
import com.app.service.ChallengeService;
import com.app.service.MailService;
import com.app.service.RSSFeedService;
import com.app.util.SCHEDULE_TYPE;

@Component
public class ChallengeNotifier {
	
	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	private ChallengeService challengeService;
	private RSSFeedService  rssFeedService;
	private MailService mailService;

	public ChallengeNotifier(
			@Autowired ChallengeService challengeService,
			@Autowired RSSFeedService rssFeedService,
			@Autowired MailService mailService) {
		this.challengeService = challengeService;
		this.rssFeedService = rssFeedService;
		this.mailService = mailService;
	}
	
	public void notifyNewChallenges(SCHEDULE_TYPE scheduleType) {
		try {
			List<Challenge> challenges = this.newChallenges(
					this.itemsToChallenges(
							this.rssFeedService.getItems()));
			this.mailService.buildMessage(challenges).send();
			logger.debug("ScheduleType: {} challenges: {}", scheduleType, challenges);
			if(scheduleType.equals(SCHEDULE_TYPE.LATER) && challenges.size() > 0) {
				this.challengeService.addChallenges(challenges);
			}
		} catch (Exception e) {
			logger.error("Error notifying new challenges {}", e);
			return;
		}
		logger.debug("Successfully notified new challenges");
	}
	
	public void notifyAllChallenges() {
		try {
			this.mailService.buildMessage(
					this.itemsToChallenges(
							this.rssFeedService.getAllItems()))
			.send();
		} catch (Exception e) {
			logger.error("Error notifying all challenges {}", e);
			return;
		}
		logger.debug("Successfully notified all challenges");
	}
	
	private List<Challenge> newChallenges(List<Challenge> challenges) {
		List<Challenge> oldChallenges = this.challengeService.getChallenges();
		Map<String, Challenge> newChallenges = new HashMap<>();
		challenges.forEach(challenge -> newChallenges.put(challenge.getName(), challenge));
		oldChallenges.forEach(oldChallenge -> {
			newChallenges.compute(oldChallenge.getName(), (name, challenge) -> null);
		});
		return new ArrayList<>(newChallenges.values());
	}
	
	private List<Challenge> itemsToChallenges(List<Item> items) {
		return items
				.stream()
				.map(item -> {
					return new Challenge(item.getTitle(), item.getDescription(), item.getLink());
				})
				.collect(Collectors.toList());
	}
}
