package com.app.controller;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.ScheduleTime;
import com.app.scheduler.ChallengeNotificationScheduler;
import com.app.util.AppUtil;
import com.app.util.ChallengeType;

@CrossOrigin
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());
            
    private ChallengeNotificationScheduler scheduler;
    
    public ScheduleController(@Autowired ChallengeNotificationScheduler scheduler) {
        LOGGER.debug("Schedule controller initialization started");
        this.scheduler = scheduler;
        LOGGER.debug("Schedule controller initialized");
    }
    
    @GetMapping("/now/{challengeType}")
    @ResponseStatus(code = HttpStatus.OK)
    public void scheduleNow(@PathVariable(required = true) ChallengeType challengeType) {
        LOGGER.info("GET /schedule/now/{}", challengeType.name());
        this.scheduler.scheduleNow(challengeType);
    }
    
    @PostMapping(path = "", produces = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public void schedule(@RequestBody(required = true) ScheduleTime scheduleTime) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("POST /schedule BODY: {}", scheduleTime.toString());
        } else {
            LOGGER.info("POST /schedule");
        }
        this.scheduler.schedule(
                AppUtil.format(scheduleTime.getHours(), scheduleTime.getMinutes()));
    }
}
