package com.coursework.clickboardbackend.notification.repository;

import com.coursework.clickboardbackend.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("SELECT n FROM Notification n WHERE n.user.username = :username AND n.isRead = false ORDER BY n.createdAt DESC")
    List<Notification> findByUserUsernameAndIsReadFalse(@Param("username") String username);
}



