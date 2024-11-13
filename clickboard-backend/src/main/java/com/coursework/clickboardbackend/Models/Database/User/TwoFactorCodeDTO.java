package com.coursework.clickboardbackend.Models.Database.User;

public class TwoFactorCodeDTO {
    private String code;
    private String username;

    public TwoFactorCodeDTO(){

    }

    public String getCode() {
        return code;
    }

    public String getUsername() {
        return username;
    }
}
