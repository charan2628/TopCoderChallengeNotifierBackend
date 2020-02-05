package com.app.scheduler;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import com.app.model.rss.Item;
import com.app.notifier.ChallengeNotifier;
import com.app.service.ErrorLogService;
import com.app.service.RSSFeedService;
import com.app.service.StatusService;
import com.app.service.UserConfigService;

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
    private ThreadPoolTaskScheduler taskScheduler;
    private UserConfigService userConfigService;
    private RSSFeedService rssFeedService;
    private long scheduleRate;
    private int scheduleSections;

    public ChallengeNotificationScheduler(
            StatusService statusService, ErrorLogService errorLogService,
            ChallengeNotifier challengeNotifier, ThreadPoolTaskScheduler taskScheduler,
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
            Instant instant = Instant.now();
            long startTime = instant.toEpochMilli(), interval = this.scheduleRate/this.scheduleSections,
                    start, end;
            for(int i = this.scheduleSections; i > 0; i--) {
                start = startTime + (i-1)*interval;
                end = startTime + i*interval;
                LOGGER.debug("Scheduling Task between {} and {}", start, end);
                this.schedule(start, end);
            }
        } catch (Exception e) {
            LOGGER.error("Error scheduling notifications");
            this.errorLogService.addErrorLog(
                    String.format("Error scheduling notifications",
                            LocalDateTime.now().toString()));
            this.statusService.error();
        }
    }

    private void schedule(long start, long end) {
        List<String> emails = this.userConfigService
                .usersWithinTime(start,
                        end);
        this.taskScheduler.schedule(() -> {
            LOGGER.debug("Running scheduled notification task emails: {}", emails);
            List<Item> items = this.rssFeedService.getItems();
            this.challengeNotifier.notifiyChallenges(emails, items);
        }, Instant.ofEpochMilli(start));
    }

}
