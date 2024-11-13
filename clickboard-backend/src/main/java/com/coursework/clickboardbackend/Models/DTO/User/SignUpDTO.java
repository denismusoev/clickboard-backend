package com.coursework.clickboardbackend.Models.DTO.User;

import com.coursework.clickboardbackend.Models.Database.User.User;

public class SignUpDTO {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String username;
    private String password;
    private String email;

    public SignUpDTO(){

    }

    public SignUpDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

