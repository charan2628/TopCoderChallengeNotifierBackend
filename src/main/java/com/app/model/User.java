package com.app.model;

import org.bson.types.ObjectId;

public class User {

    private ObjectId id;
    private String email;
    private String password;
    private String confirmToken;
    private boolean confirmed;

    public User() {
        super();
    }

    public User(String email, String password, String confirmToken, boolean confirmed) {
        super();
        this.email = email;
        this.password = password;
        this.confirmToken = confirmToken;
        this.confirmed = confirmed;
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmToken() {
        return this.confirmToken;
    }

    public void setConfirmToken(String confirmToken) {
        this.confirmToken = confirmToken;
    }

    public boolean isConfirmed() {
        return this.confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public String toString() {
        return "User [id=" + this.id + ", email=" + this.email + ", password=" + this.password + ", confirmToken="
                + this.confirmToken + ", confirmed=" + this.confirmed + "]";
    }

}
