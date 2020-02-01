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
import com.app.util.ChallengeType;
import com.app.util.ScheduleType;

/**
 * Scheduler which schedule notifications now or later
 *
 * @author charan2628
 *
 */
@Component
public class ChallengeNotificationScheduler {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private ChallengeNotifier challengeNotifier;
    private ScheduledFuture<?> scheduledFuture;

    public ChallengeNotificationScheduler(
            @Autowired ChallengeNotifier challengeNotifier) {
        this.challengeNotifier = challengeNotifier;
    }

    /**
     * Notified challenges now
     *
     * @param challengeType NEW / ALL
     */
    public void scheduleNow(ChallengeType challengeType) {
        if (challengeType.equals(ChallengeType.NEW)) {
            this.challengeNotifier.notifyNewChallenges(ScheduleType.NOW);
        } else {
            this.challengeNotifier.notifyAllChallenges();
        }
    }

    /**
     * Notifies challenges daily at specified time
     *
     * @param date
     */
    public synchronized void schedule(Date date) {
        try {
            if (this.scheduledFuture != null) {
                this.scheduledFuture.cancel(true);
                this.scheduledFuture = null;
            }
            TaskScheduler scheduler = new DefaultManagedTaskScheduler();
            this.scheduledFuture = scheduler.scheduleAtFixedRate(
                    () -> this.challengeNotifier.notifyNewChallenges(ScheduleType.LATER),
                    date,
                    86400000L);
        } catch (Exception e) {
            LOGGER.error("Error scheduling task date: {} {}", date, e);
            throw new ErrorSchedulingTaskException();
        }
        LOGGER.info("Successfully scheduled task for date: {}", date);
    }

}
