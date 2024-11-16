package com.coursework.clickboardbackend.user.dto;

import com.coursework.clickboardbackend.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupDto {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String phone;

    public SignupDto(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.phone = user.getPhone();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }
}

