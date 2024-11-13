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
        this.deposit = user.getDeposit();
        this.isTwoFactorEnabled = user.isTwoFactorEnabled();
        this.isChildModeEnabled = user.isChildModeEnabled();
        this.areNotificationsEnabled = user.isAreNotificationsEnabled();
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

//    public String getUsername() {
//        return username;
//    }
//
//    public String getPassword() {
//        return password;
//    }

    public float getDeposit() {
        return deposit;
    }

//    public String getEmail() {
//        return email;
//    }

    public boolean isTwoFactorEnabled() {
        return isTwoFactorEnabled;
    }

//    public String getTwoFactorCode() {
//        return twoFactorCode;
//    }

//    public LocalDateTime getTwoFactorExpiration() {
//        return twoFactorExpiration;
//    }

    public boolean isChildModeEnabled() {
        return isChildModeEnabled;
    }

    public boolean isAreNotificationsEnabled() {
        return areNotificationsEnabled;
    }

//    public ShoppingCart getShoppingCart() {
//        return shoppingCart;
//    }


//    public List<NotificationDTO> getNotificationList() {
//        return notificationList;
//    }
}
