package com.coursework.clickboardbackend.Models.DTO.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTokenDTO extends ApiResponse {
    private String token;
    private String username;
    @JsonProperty("isChildModeEnabled")
    private boolean isChildModeEnabled;
    @JsonProperty("isVk")
    private boolean isVk;

    public UserTokenDTO(String token, String username, boolean success, String message, boolean isChildModeEnabled, boolean isVk) {
        super(success, message);
        this.token = token;
        this.username = username;
        this.isChildModeEnabled = isChildModeEnabled;
        this.isVk = isVk;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isChildModeEnabled() {
        return isChildModeEnabled;
    }

    public void setChildModeEnabled(boolean childModeEnabled) {
        isChildModeEnabled = childModeEnabled;
    }

    public boolean isVk() {
        return isVk;
    }

    public void setVk(boolean vk) {
        isVk = vk;
    }
}
