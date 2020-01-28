package com.app.controller;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.ScheduleTime;
import com.app.scheduler.ChallengeNotificationScheduler;
import com.app.util.AppUtil;
import com.app.util.CHALLENGE_TYPE;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
			
	private ChallengeNotificationScheduler scheduler;
	
	public ScheduleController(
			@Autowired ChallengeNotificationScheduler scheduler) {
		logger.debug("Schedule controller initialization started");
		this.scheduler = scheduler;
		logger.debug("Schedule controller initialized");
	}
	
	@GetMapping("/now/{challengeType}")
	public String scheduleNow(
			@PathVariable(required = true) CHALLENGE_TYPE challengeType) {
		logger.info("GET /schedule/now/{}", challengeType.name());
		this.scheduler.scheduleNow(challengeType);
		return "SENT";
	}
	
	@PostMapping(path = "", produces = "application/json")
	public String schedule(
			@RequestBody(required = true) ScheduleTime scheduleTime) {
		if(logger.isDebugEnabled()) {
			logger.debug("POST /schedule BODY: {}", scheduleTime.toString());
		} else {
			logger.info("POST /schedule");
		}
		this.scheduler.schedule(
				AppUtil.format(scheduleTime.getHours(), scheduleTime.getMinutes()));
		return "SCHEDULED";
	}
}
