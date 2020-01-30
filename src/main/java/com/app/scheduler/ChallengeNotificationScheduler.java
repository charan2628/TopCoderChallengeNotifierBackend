package com.app.scheduler;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.stereotype.Component;

import com.app.exception.ErrorSchedulingTaskException;
import com.app.notifier.ChallengeNotifier;
import com.app.util.CHALLENGE_TYPE;
import com.app.util.SCHEDULE_TYPE;

@Component
public class ChallengeNotificationScheduler {
	
	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private ChallengeNotifier challengeNotifier;
	private ScheduledFuture<?> scheduledFuture;

	public ChallengeNotificationScheduler(
			@Autowired ChallengeNotifier challengeNotifier) {
		this.challengeNotifier = challengeNotifier;
	}

	public void scheduleNow(CHALLENGE_TYPE challengeType) {
		if(challengeType.equals(CHALLENGE_TYPE.NEW)) {
			this.challengeNotifier.notifyNewChallenges(SCHEDULE_TYPE.NOW);
		} else {
			this.challengeNotifier.notifyAllChallenges();
		}
	}

	public synchronized void schedule(Date date) {
		try {
			if(this.scheduledFuture != null) {
				this.scheduledFuture.cancel(true);
				this.scheduledFuture = null;
			}
			TaskScheduler scheduler = new DefaultManagedTaskScheduler();
			this.scheduledFuture = scheduler.scheduleAtFixedRate(
					() -> this.challengeNotifier.notifyNewChallenges(SCHEDULE_TYPE.LATER),
					date,
					86400000L);
		} catch (Exception e) {
			logger.error("Error scheduling task date: {} {}", date, e);
			throw new ErrorSchedulingTaskException();
		}
		logger.info("Successfully scheduled task for date: {}", date);
	}
	
}
