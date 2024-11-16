package com.coursework.clickboardbackend.user.Controller;

import com.coursework.clickboardbackend.user.dto.ResponseDTO;
import com.coursework.clickboardbackend.user.dto.SigninDto;
import com.coursework.clickboardbackend.user.dto.SignupDto;
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
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody SignupDto signUpDTO) {
        userService.registerUser(signUpDTO);
        return ResponseEntity.ok(new ResponseDTO(true, "Регистрация прошла успешно!") {
        });
    }

    @PostMapping("/authenticate")
    public CompletableFuture<ResponseEntity<ResponseDTO>> authenticate(@RequestBody SigninDto request) {
        return authService.authenticateUser(request)
                .thenApply(ResponseEntity::ok)
                .exceptionally(e -> {
                    Throwable cause = e.getCause();
                    if (cause instanceof BadCredentialsException) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false, cause.getMessage()) {
                        });
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(false, cause.getMessage()) {
                        });
                    }
                });
    }

}

