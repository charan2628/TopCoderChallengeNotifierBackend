package com.app.notifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

import com.app.AppRunner;
import com.app.model.Challenge;
import com.app.model.rss.Item;
import com.app.service.ChallengeService;
import com.app.service.MailService;
import com.app.service.MailService.Mail;
import com.app.service.RSSFeedService;
import com.app.service.RSSFeedTestData;
import com.app.util.ScheduleType;

@ActiveProfiles("test")
@SpringBootTest(classes = AppRunner.class)
public class ChallengeNotifierTest extends AbstractTestNGSpringContextTests {

	private RSSFeedService rssFeedService;
	private MailService mailService;
	private MailService.Mail mail;
	private ChallengeService challengeService;
	
	@BeforeTest
	public void setUp() throws IOException {
		this.rssFeedService = mock(RSSFeedService.class);
		this.challengeService = mock(ChallengeService.class);
		this.mailService = mock(MailService.class);
		this.mail = mock(Mail.class);
		when(this.mailService.buildMessage(ArgumentMatchers.<ArrayList<Challenge>>any())).thenReturn(this.mail);
	}
	
	@Test
	public void newChallengesTest() throws IOException {
		List<Item> items = new ArrayList<>();
		items.add(RSSFeedTestData.items().get(0)); items.add(RSSFeedTestData.items().get(1));
		items.add(RSSFeedTestData.items().get(2));
		when(this.rssFeedService.getItems()).thenReturn(items);
		
		List<Challenge> challenges = new ArrayList<>();
		challenges.add(
				new Challenge(RSSFeedTestData.items().get(0).getTitle(), RSSFeedTestData.items().get(0).getDescription(), 
						RSSFeedTestData.items().get(0).getLink()));
		challenges.add(
				new Challenge(RSSFeedTestData.items().get(1).getTitle(), RSSFeedTestData.items().get(1).getDescription(), 
						RSSFeedTestData.items().get(1).getLink()));
		when(this.challengeService.getChallenges()).thenReturn(challenges);
		
		ChallengeNotifier challangeNotifier = new ChallengeNotifier(this.challengeService, this.rssFeedService, this.mailService);
		challangeNotifier.notifyNewChallenges(ScheduleType.NOW);
		
		List<Challenge> newChallenges = new ArrayList<>(); 
		newChallenges.add(
				new Challenge(RSSFeedTestData.items().get(2).getTitle(), RSSFeedTestData.items().get(2).getDescription(), 
						RSSFeedTestData.items().get(2).getLink()));
		verify(this.mailService).buildMessage(eq(newChallenges));
	}
	
	@Test
	public void allChallengesTest() throws IOException {
		List<Item> items = new ArrayList<>();
		items.add(RSSFeedTestData.items().get(0)); items.add(RSSFeedTestData.items().get(1));
		items.add(RSSFeedTestData.items().get(2));
		when(this.rssFeedService.getAllItems()).thenReturn(items);
		
		List<Challenge> allChallenges = new ArrayList<>();
		allChallenges.add(
				new Challenge(RSSFeedTestData.items().get(0).getTitle(), RSSFeedTestData.items().get(0).getDescription(), 
						RSSFeedTestData.items().get(0).getLink()));
		allChallenges.add(
				new Challenge(RSSFeedTestData.items().get(1).getTitle(), RSSFeedTestData.items().get(1).getDescription(), 
						RSSFeedTestData.items().get(1).getLink()));
		allChallenges.add(
				new Challenge(RSSFeedTestData.items().get(2).getTitle(), RSSFeedTestData.items().get(2).getDescription(), 
						RSSFeedTestData.items().get(2).getLink()));
		
		ChallengeNotifier challangeNotifier = new ChallengeNotifier(this.challengeService, this.rssFeedService, this.mailService);
		challangeNotifier.notifyAllChallenges();
		
		verify(this.mailService).buildMessage(eq(allChallenges));
	}
	
}
