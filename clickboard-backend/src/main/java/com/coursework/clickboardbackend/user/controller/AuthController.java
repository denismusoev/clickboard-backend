package com.coursework.clickboardbackend.user.controller;

import com.coursework.clickboardbackend.user.dto.ResponseDto;
import com.coursework.clickboardbackend.user.dto.SigninDto;
import com.coursework.clickboardbackend.user.dto.SignupDto;
import com.coursework.clickboardbackend.user.model.User;
import com.coursework.clickboardbackend.user.service.AuthService;
import com.coursework.clickboardbackend.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@RequestBody SignupDto signUpDTO) {
        userService.registerUser(signUpDTO);
        return ResponseEntity.ok(new ResponseDto(true, "Регистрация прошла успешно!") {
        });
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDto> authenticate(@RequestBody SigninDto request) {
        try {
            ResponseDto res = authService.authenticateUser(request);
            return ResponseEntity.ok(res);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(false, e.getMessage()){});
        } catch (Exception e) {
            String errorMessage = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(false, errorMessage){});
        }
    }

    @PostMapping("/create-moderator")
    public ResponseEntity<User> createModerator() {
        User newModerator = userService.createModerator();
        return ResponseEntity.ok(newModerator);
    }
}

