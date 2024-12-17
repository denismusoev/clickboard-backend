package com.coursework.clickboardbackend.notification;

import com.coursework.clickboardbackend.ad.model.Ad;
import com.coursework.clickboardbackend.notification.repository.NotificationRepository;
import com.coursework.clickboardbackend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void createNotification(User user, Ad ad, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setAd(ad);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }


    public List<Notification> getUnreadNotificationsByUsername(String username) {
        return notificationRepository.findByUserUsernameAndIsReadFalse(username);
    }

    public void markAsRead(int notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }
}

