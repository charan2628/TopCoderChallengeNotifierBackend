package com.app.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class AppUtil {

	public static Date format(int hours, int minutes) {
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.withHour(hours);
		localDateTime = localDateTime.withMinute(minutes);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static List<String> removeDups(List<String> list) {
		return new ArrayList<String>(new HashSet<String>(list));
	}
}
