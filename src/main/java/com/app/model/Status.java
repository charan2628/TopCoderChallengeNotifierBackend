package com.app.model;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Status {

    @JsonIgnore
    private ObjectId id;
    private int successfullTasks;
    private int failedTasks;
    private int errors;

    public Status() {
        super();
    }

    public Status(int successfullTasks, int failedTasks, int errors) {
        super();
        this.successfullTasks = successfullTasks;
        this.failedTasks = failedTasks;
        this.errors = errors;
    }

    public ObjectId getId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getSuccessfullTaskss() {
        return this.successfullTasks;
    }

    public void setSuccessfullTasks(int successfullTasks) {
        this.successfullTasks = successfullTasks;
    }

    public int getFailedTasks() {
        return this.failedTasks;
    }

    public void setFailedTasks(int failedTasks) {
        this.failedTasks = failedTasks;
    }

    public int getErrors() {
        return this.errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

}
