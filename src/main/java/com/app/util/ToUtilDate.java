package com.app.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ToUtilDate {

	public static Date format(int hours, int minutes) {
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.withHour(hours);
		localDateTime = localDateTime.withMinute(minutes);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}
