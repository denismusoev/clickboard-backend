package com.coursework.clickboardbackend.Models.DTO.User;

import com.coursework.clickboardbackend.Models.Database.User.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserViewDTO {
    private String firstName;
    private String lastName;
    private String patronymic;
    private boolean isTwoFactorEnabled;
    private float deposit;
    @JsonProperty("isChildModeEnabled")
    private boolean isChildModeEnabled;
    private boolean areNotificationsEnabled;

    public UserViewDTO(){

    }

    public UserViewDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public float getDeposit() {
        return deposit;
    }


    public boolean isTwoFactorEnabled() {
        return isTwoFactorEnabled;
    }

    public boolean isChildModeEnabled() {
        return isChildModeEnabled;
    }

    public boolean isAreNotificationsEnabled() {
        return areNotificationsEnabled;
    }
}
