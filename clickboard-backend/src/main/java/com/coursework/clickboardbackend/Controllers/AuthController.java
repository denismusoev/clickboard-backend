package com.coursework.clickboardbackend.Controllers;

import com.coursework.clickboardbackend.Models.DTO.User.ApiResponse;
import com.coursework.clickboardbackend.Models.DTO.User.SignInDTO;
import com.coursework.clickboardbackend.Models.DTO.User.SignUpDTO;
import com.coursework.clickboardbackend.Models.Database.User.TwoFactorCodeDTO;
import com.coursework.clickboardbackend.Services.AuthService;
import com.coursework.clickboardbackend.Services.UserService;
import com.coursework.clickboardbackend.Utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<ApiResponse> registerUser(@RequestBody SignUpDTO signUpDTO) {
        userService.registerUser(signUpDTO);
        return ResponseEntity.ok(new ApiResponse(true, "Регистрация прошла успешно!") {
        });
    }

    @PostMapping("/authenticate")
    public CompletableFuture<ResponseEntity<ApiResponse>> authenticate(@RequestBody SignInDTO request) {
        return authService.authenticateUser(request)
                .thenApply(ResponseEntity::ok)
                .exceptionally(e -> {
                    Throwable cause = e.getCause();
                    if (cause instanceof BadCredentialsException) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, cause.getMessage()) {
                        });
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, cause.getMessage()) {
                        });
                    }
                });
    }

}

