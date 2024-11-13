package com.coursework.clickboardbackend.Services;

import com.coursework.clickboardbackend.Models.DTO.User.ApiResponse;
import com.coursework.clickboardbackend.Models.DTO.User.SignInDTO;
import com.coursework.clickboardbackend.Models.DTO.User.UserTokenDTO;
import com.coursework.clickboardbackend.Models.Database.User.User;
import com.coursework.clickboardbackend.Utils.JwtUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
@Service
@Transactional
public class AuthService{
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

    @Async("taskExecutor")
    public CompletableFuture<ApiResponse> authenticateUser(SignInDTO request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UserDetails userDetails;
                User user;
                boolean isVk = false;
                try{
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
                } catch (Exception e){
                    throw new BadCredentialsException("Неверный логин или пароль");
                }
                user = userService.getByUsername(request.getUsername());
                userDetails = userService.loadUserByUsername(user.getUsername());
                return new UserTokenDTO(jwtUtil.generateToken(userDetails), user.getUsername(), true, "token", isVk);
            } catch (Exception e) {
                throw new CompletionException(new BadCredentialsException(e.getMessage()));
            }
        });
    }
}
