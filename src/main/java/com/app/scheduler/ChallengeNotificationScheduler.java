package com.app.scheduler;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import com.app.model.Challenge;
import com.app.notifier.ChallengeNotifier;
import com.app.service.ErrorLogService;
import com.app.service.RSSFeedService;
import com.app.service.StatusService;
import com.app.service.UserConfigService;
import com.app.util.AppUtil;
import com.app.util.Constants;

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

    public ChallengeNotificationScheduler(
            StatusService statusService, ErrorLogService errorLogService,
            ChallengeNotifier challengeNotifier, ThreadPoolTaskScheduler taskScheduler,
            UserConfigService userConfigService, RSSFeedService rssFeedService) {
        super();
        this.statusService = statusService;
        this.errorLogService = errorLogService;
        this.challengeNotifier = challengeNotifier;
        this.taskScheduler = taskScheduler;
        this.userConfigService = userConfigService;
        this.rssFeedService = rssFeedService;
    }
    
    @Async
    @Scheduled(fixedRate = 3*Constants.TEN_MINUTES_IN_MILLI)
    public void scheduleNotifications() {
        try {
            Instant instant = Instant.now();
            long startTime = instant.getEpochSecond()*Constants.ONE_SECOND_IN_MILLI;
            
            this.schedule(startTime + 2*Constants.TEN_MINUTES_IN_MILLI,
                    startTime + 3*Constants.TEN_MINUTES_IN_MILLI);
            this.schedule(startTime + Constants.TEN_MINUTES_IN_MILLI,
                    startTime + 2*Constants.TEN_MINUTES_IN_MILLI);
            this.schedule(startTime,
                    startTime + Constants.TEN_MINUTES_IN_MILLI);
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
            List<Challenge> challenges = AppUtil.itemsToChallenges(
                    this.rssFeedService.getItems());
            this.challengeNotifier.notifiyChallenges(emails, challenges);
        }, Instant.ofEpochMilli(start));
    }

}
