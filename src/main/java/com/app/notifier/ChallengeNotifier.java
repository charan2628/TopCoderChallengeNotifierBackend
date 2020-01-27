package com.app.notifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.model.Challenge;
import com.app.model.rss.Item;
import com.app.service.ChallengeService;
import com.app.service.MailService;
import com.app.service.RSSFeedService;

@Component
public class ChallengeNotifier {
	
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
	
	public void notifyNewChallenges() {
		try {
			this.mailService.buildMessage(
					this.newChallenges(
							this.itemsToChallenges(
									this.rssFeedService.getItems())))
			.send();
		} catch (IOException e) {
			// TODO add logging sysout for now
			e.printStackTrace();
		}
	}
	
	public void notifyAllChallenges() {
		try {
			this.mailService.buildMessage(
					this.itemsToChallenges(
							this.rssFeedService.getAllItems()))
			.send();
		} catch (IOException e) {
			// TODO add logging sysout for now
			e.printStackTrace();
		}
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
