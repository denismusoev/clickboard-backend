package com.coursework.clickboardbackend.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationDto {
    private int id;
    private String message;
    private LocalDateTime createdAt;
    private int adId; // ID объявления
}
