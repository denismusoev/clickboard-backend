package com.coursework.clickboardbackend.user.service;

import com.coursework.clickboardbackend.utils.JwtUtil;
import com.coursework.clickboardbackend.user.dto.UserTokenDto;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletionException;

import com.coursework.clickboardbackend.user.dto.SigninDto;
import com.coursework.clickboardbackend.user.dto.ResponseDto;
import com.coursework.clickboardbackend.user.model.User;

@Component
@Service
@Transactional
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public ResponseDto authenticateUser(SigninDto request) {
        try {
            UserDetails userDetails;
            User user;
            try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            } catch (Exception e){
                throw new BadCredentialsException("Неверный логин или пароль");
            }
            user = userService.getByUsername(request.getUsername());
            userDetails = userService.loadUserByUsername(user.getUsername());
            return new UserTokenDto(jwtUtil.generateToken(userDetails), user.getUsername(), user.getRole().toString());
        } catch (Exception e) {
            throw new CompletionException(new BadCredentialsException(e.getMessage()));
        }
    }
}
