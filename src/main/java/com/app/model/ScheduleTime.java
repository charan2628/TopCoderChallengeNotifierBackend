package com.app.model;

public class ScheduleTime {

	private int hours;
	private int minutes;
	
	public ScheduleTime() {
		super();
	}

	public ScheduleTime(int hours, int minutes) {
		super();
		this.hours = hours;
		this.minutes = minutes;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	@Override
	public String toString() {
		return "ScheduleTime [hours=" + hours + ", minutes=" + minutes + "]";
	}
}
