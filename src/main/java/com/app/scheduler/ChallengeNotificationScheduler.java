package com.app.scheduler;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.model.rss.Item;
import com.app.notifier.ChallengeNotifier;
import com.app.service.ErrorLogService;
import com.app.service.RSSFeedService;
import com.app.service.StatusService;
import com.app.service.UserConfigService;
import com.app.util.Constants;

import static com.app.util.AppUtil.*;

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

    private StatusService statusService;
    private ErrorLogService errorLogService;
    private ChallengeNotifier challengeNotifier;
    private ScheduledExecutorService taskScheduler;
    private UserConfigService userConfigService;
    private RSSFeedService rssFeedService;
    private long scheduleRate;
    private int scheduleSections;

    public ChallengeNotificationScheduler(
            StatusService statusService, ErrorLogService errorLogService,
            ChallengeNotifier challengeNotifier, @Qualifier("task_scheduler")ScheduledExecutorService taskScheduler,
            UserConfigService userConfigService, RSSFeedService rssFeedService,
            @Value("${schedule_sections}") int scheduleSections, @Value("${schedule_rate}") long scheduleRate) {
        super();
        this.statusService = statusService;
        this.errorLogService = errorLogService;
        this.challengeNotifier = challengeNotifier;
        this.taskScheduler = taskScheduler;
        this.userConfigService = userConfigService;
        this.rssFeedService = rssFeedService;
        this.scheduleSections = scheduleSections;
        this.scheduleRate = scheduleRate;
    }

    @Async
    @Scheduled(fixedRateString = "${schedule_rate}")
    public void scheduleNotifications() {
        try {
            long startTime = timeNow(), interval = (this.scheduleRate/this.scheduleSections)/1000,
                    start, end, delay;
            for(int i = this.scheduleSections; i > 0; i--) {
                delay = (i-1)*interval;
                start = (startTime + delay) % Constants.ONE_DAY_IN_SECONDS;
                end = (startTime + delay + interval) % Constants.ONE_DAY_IN_SECONDS;
                LOGGER.info("Scheduling Task between {} and {}", start, end);
                this.schedule(start, end, delay);
            }
        } catch (Exception e) {
            LOGGER.error("Error scheduling notifications");
            this.errorLogService.addErrorLog(
                    String.format("Error scheduling notifications",
                            LocalDateTime.now().toString()));
            this.statusService.error();
        }
    }

    private void schedule(long start, long end, long delay) {
        List<String> emails = this.userConfigService
                .usersWithinTime(start,
                        end);
        if(emails.size() == 0) {
            LOGGER.info("No users scheduled between {} and {}", start, end);
            return;
        }
        this.taskScheduler.schedule(() -> {
            LOGGER.info("Running scheduled notification task emails: {}", emails);
            List<Item> items = this.rssFeedService.getItems();
            this.challengeNotifier.notifiyChallenges(emails, items);
        }, delay, TimeUnit.SECONDS);
    }

}
