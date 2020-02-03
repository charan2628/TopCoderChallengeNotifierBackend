package com.app.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class UserConfig {

    private ObjectId id;
    private String email;
    private List<String> tags;
    private String scheduleTime;
    private List<String> notifiedChallenges;

    public UserConfig() {
        super();
    }

    public UserConfig(String email, List<String> tags, String scheduleTime, List<String> notifiedChallenges) {
        super();
        this.email = email;
        this.tags = new ArrayList<String>(tags);
        this.scheduleTime = scheduleTime;
        this.notifiedChallenges = new ArrayList<String>(notifiedChallenges);
    }

    public ObjectId getId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(List<String> tags) {
        this.tags = new ArrayList<String>(tags);
    }

    public String getScheduleTime() {
        return this.scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public List<String> getNotifiedChallenges() {
        return this.notifiedChallenges;
    }

    public void setNotifiedChallenges(List<String> notifiedChallenges) {
        this.notifiedChallenges = new ArrayList<String>(notifiedChallenges);
    }

    @Override
    public String toString() {
        return "UserConfig [id=" + this.id + ", email=" + this.email + ", tags=" + this.tags
                + ", scheduleTime=" + this.scheduleTime + ", notifiedChallenges=" + this.notifiedChallenges + "]";
    }

}
