package com.coursework.clickboardbackend.notification.repository;

import com.coursework.clickboardbackend.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserUsernameAndIsReadFalse(String username);
}



