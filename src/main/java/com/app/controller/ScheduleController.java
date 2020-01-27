package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.ScheduleTime;
import com.app.scheduler.ChallengeNotificationScheduler;
import com.app.util.CHALLENGE_TYPE;
import com.app.util.ToUtilDate;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	private ChallengeNotificationScheduler scheduler;
	
	public ScheduleController(
			@Autowired ChallengeNotificationScheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	@GetMapping("/now/{challengeType}")
	public String scheduleNow(
			@PathVariable(required = true) CHALLENGE_TYPE challengeType) {
		this.scheduler.scheduleNow(challengeType);
		return "SENT";
	}
	
	@PostMapping(path = "", produces = "application/json")
	public String schedule(
			@RequestBody(required = true) ScheduleTime scheduleTime) {
		this.scheduler.schedule(
				ToUtilDate.format(scheduleTime.getHours(), scheduleTime.getMinutes()));
		return "SCHEDULED";
	}
}
