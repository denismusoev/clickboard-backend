package com.coursework.clickboardbackend.Models.DTO.User;

public class UpdateSettingsDTO {
    private boolean isEnabled;
    private String password;

    public UpdateSettingsDTO() {
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public String getPassword() {
        return password;
    }
}
