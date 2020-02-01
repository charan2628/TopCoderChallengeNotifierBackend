package com.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {

    @JsonProperty("access_token")
    private String accessToken;

    public Token() {
        super();
    }

    public Token(String accessToken) {
        super();
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
