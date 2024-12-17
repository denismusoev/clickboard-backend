package com.coursework.clickboardbackend.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications(@AuthenticationPrincipal UserDetails userDetails) {
        // Извлекаем имя пользователя из токена
        String username = userDetails.getUsername();

        // Получаем уведомления и преобразуем их в DTO
        List<NotificationDto> notifications = notificationService
                .getUnreadNotificationsByUsername(username)
                .stream()
                .map(notification -> new NotificationDto(
                        notification.getId(),
                        notification.getMessage(),
                        notification.getCreatedAt(),
                        notification.getAd() != null ? notification.getAd().getId() : 0 // Проверка на null
                ))
                .toList();

        return ResponseEntity.ok(notifications);
    }


    @PostMapping("/read/{id}")
    public ResponseEntity<Void> markAsRead(@PathVariable int id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}

