package com.app.model;

public class ScheduleTime {

    private long epochMilli;

    public ScheduleTime() {
        super();
    }

    public ScheduleTime(long epochMilli) {
        super();
        this.epochMilli = epochMilli;
    }

    public long getEpochMilli() {
        return this.epochMilli;
    }

    public void setEpochMilli(long epochMilli) {
        this.epochMilli = epochMilli;
    }

}
