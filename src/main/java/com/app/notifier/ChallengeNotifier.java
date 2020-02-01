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

/**
 * Notified new / all challenges
 * 
 * @author charan2628
 *
 */
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
	
	/**
	 * Notifies new challenges 
	 * if scheduled type is now won't save challenges to db
	 * else if scheduled type is later then saves challenges to db
	 * 
	 * @param scheduleType
	 */
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
	
	/**
	 * Notified all the challenges from the feed
	 */
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
	
	/**
	 * Helper method to get new challenges by filtering out old challenges
	 * 
	 * The method uses HashMap's compute method to 
	 * efficiently do set subtraction
	 * 
	 * @param all challenges
	 * @return new challenges
	 */
	private List<Challenge> newChallenges(List<Challenge> challenges) {
		List<Challenge> oldChallenges = this.challengeService.getChallenges();
		Map<String, Challenge> newChallenges = new HashMap<>();
		challenges.forEach(challenge -> newChallenges.put(challenge.getName(), challenge));
		oldChallenges.forEach(oldChallenge -> {
			newChallenges.compute(oldChallenge.getName(), (name, challenge) -> null);
		});
		return new ArrayList<>(newChallenges.values());
	}
	
	/**
	 * Helper method to map RSS Feed Items to Challenges
	 * 
	 * @param list of items
	 * @return list of challenges
	 */
	private List<Challenge> itemsToChallenges(List<Item> items) {
		return items
				.stream()
				.map(item -> {
					return new Challenge(item.getTitle(), item.getDescription(), item.getLink());
				})
				.collect(Collectors.toList());
	}
}
