package com.app.model;

public class ErrorMessage {

    private String msg;

    public ErrorMessage() {
        super();
    }

    public ErrorMessage(String msg) {
        super();
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ErrorMessage [msg=" + this.msg + "]";
    }

}
