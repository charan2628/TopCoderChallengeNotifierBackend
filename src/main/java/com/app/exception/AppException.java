package com.app.exception;

public class AppException {

    private String timestamp;
    private String status;
    private String message;
    private String path;
    
    public AppException() {
        super();
    }

    public AppException(String timestamp, String status, String message, String path) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
