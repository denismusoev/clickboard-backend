package com.coursework.clickboardbackend.Models.DTO.User;

public class UserUpdateDTO {
    private String firstname;
    private String lastname;
    private String patronymic;
    private String password;

    public UserUpdateDTO() {
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPassword() {
        return password;
    }
}
