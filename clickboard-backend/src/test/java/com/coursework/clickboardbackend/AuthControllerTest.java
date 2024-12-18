package com.coursework.clickboardbackend;

import com.coursework.clickboardbackend.user.controller.AuthController;
import com.coursework.clickboardbackend.user.dto.ResponseDto;
import com.coursework.clickboardbackend.user.dto.SigninDto;
import com.coursework.clickboardbackend.user.dto.SignupDto;
import com.coursework.clickboardbackend.user.model.User;
import com.coursework.clickboardbackend.user.service.AuthService;
import com.coursework.clickboardbackend.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        // Arrange
        SignupDto signupDto = new SignupDto();
        doNothing().when(userService).registerUser(any(SignupDto.class));

        // Act
        ResponseEntity<ResponseDto> response = authController.registerUser(signupDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Регистрация прошла успешно!", response.getBody().getMessage());
        verify(userService, times(1)).registerUser(any(SignupDto.class));
    }

    @Test
    void testAuthenticate_Success() throws Exception {
        // Arrange
        SigninDto signinDto = new SigninDto();
        ResponseDto responseDto = new ResponseDto(true, "Успешная аутентификация"){};
        when(authService.authenticateUser(any(SigninDto.class))).thenReturn(responseDto);

        // Act
        ResponseEntity<ResponseDto> response = authController.authenticate(signinDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Успешная аутентификация", response.getBody().getMessage());
        verify(authService, times(1)).authenticateUser(any(SigninDto.class));
    }

    @Test
    void testAuthenticate_BadCredentials() throws Exception {
        // Arrange
        SigninDto signinDto = new SigninDto();
        BadCredentialsException badCredentialsException = new BadCredentialsException("Invalid credentials");
        when(authService.authenticateUser(any(SigninDto.class))).thenThrow(badCredentialsException);

        // Act
        ResponseEntity<ResponseDto> response = authController.authenticate(signinDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Invalid credentials", response.getBody().getMessage());
        verify(authService, times(1)).authenticateUser(any(SigninDto.class));
    }

    @Test
    void testAuthenticate_InternalServerError() {
        // Arrange
        SigninDto signinDto = new SigninDto();
        RuntimeException genericException = new RuntimeException("Internal server error");
        when(authService.authenticateUser(any(SigninDto.class))).thenThrow(genericException);

        // Act
        ResponseEntity<ResponseDto> response = authController.authenticate(signinDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Internal server error", response.getBody().getMessage());
        verify(authService, times(1)).authenticateUser(any(SigninDto.class));
    }



    @Test
    void testCreateModerator() {
        // Arrange
        User moderator = new User();
        moderator.setUsername("moderator");
        when(userService.createModerator()).thenReturn(moderator);

        // Act
        ResponseEntity<User> response = authController.createModerator();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(moderator, response.getBody());
        verify(userService, times(1)).createModerator();
    }
}
