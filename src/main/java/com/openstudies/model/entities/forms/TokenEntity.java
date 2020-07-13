package com.openstudies.model.entities.forms;

public class TokenEntity {
    private String token;

    public TokenEntity(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}