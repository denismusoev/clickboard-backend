package com.coursework.clickboardbackend;

import com.coursework.clickboardbackend.ad.model.Ad;
import com.coursework.clickboardbackend.notification.Notification;
import com.coursework.clickboardbackend.notification.NotificationController;
import com.coursework.clickboardbackend.notification.NotificationDto;
import com.coursework.clickboardbackend.notification.NotificationService;
import com.coursework.clickboardbackend.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationService notificationService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(userDetails.getUsername()).thenReturn("testUser");
    }

    @Test
    void testGetUnreadNotifications() {
        // Arrange
        User user = new User();
        Ad ad = new Ad();
        List<Notification> notifications = List.of(
                new Notification(1, user, ad, "Test message", LocalDateTime.of(2024, 8, 12, 10, 12), false)
        );

        List<NotificationDto> expectedDtos = List.of(
                new NotificationDto(1, "Test message", LocalDateTime.of(2024, 8, 12, 10, 12), 0)
        );

        when(notificationService.getUnreadNotificationsByUsername("testUser")).thenReturn(notifications);

        // Act
        ResponseEntity<List<NotificationDto>> response = notificationController.getUnreadNotifications(userDetails);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDtos, response.getBody());
        verify(notificationService, times(1)).getUnreadNotificationsByUsername("testUser");
    }


    @Test
    void testMarkAsRead() {
        // Arrange
        int notificationId = 1;
        doNothing().when(notificationService).markAsRead(notificationId);

        // Act
        ResponseEntity<Void> response = notificationController.markAsRead(notificationId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(notificationService, times(1)).markAsRead(notificationId);
    }
}
