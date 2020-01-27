package com.app.scheduler;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.stereotype.Component;

import com.app.notifier.ChallengeNotifier;
import com.app.util.CHALLENGE_TYPE;

@Component
public class ChallengeNotificationScheduler {

	private ChallengeNotifier challengeNotifier;
	private ScheduledFuture<?> scheduledFuture;

	public ChallengeNotificationScheduler(
			@Autowired ChallengeNotifier challengeNotifier) {
		this.challengeNotifier = challengeNotifier;
	}

	public void scheduleNow(CHALLENGE_TYPE challengeType) {
		if(challengeType.equals(CHALLENGE_TYPE.NEW)) {
			this.challengeNotifier.notifyNewChallenges();
		} else {
			this.challengeNotifier.notifyAllChallenges();
		}
	}

	public synchronized void schedule(Date date) {
		if(this.scheduledFuture != null) {
			this.scheduledFuture.cancel(true);
			this.scheduledFuture = null;
		}
		TaskScheduler scheduler = new DefaultManagedTaskScheduler();
		this.scheduledFuture = scheduler.scheduleAtFixedRate(
				() -> this.challengeNotifier.notifyNewChallenges(),
				date,
				86400000L);
	}
	
}
