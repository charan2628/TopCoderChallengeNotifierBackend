package com.app.model.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Claims {

    @JsonProperty("iss")
    private String issuer;
    @JsonProperty("exp")
    private long expirationTime;
    @JsonProperty("email")
    private String email;

    public Claims() {
        super();
    }

    public Claims(String issuer, long expirationTime, String email) {
        super();
        this.issuer = issuer;
        this.expirationTime = expirationTime;
        this.email = email;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
