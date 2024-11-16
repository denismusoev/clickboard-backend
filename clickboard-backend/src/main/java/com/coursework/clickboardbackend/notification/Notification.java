package com.coursework.clickboardbackend.notification;

import com.coursework.clickboardbackend.ad.model.Ad;
import com.coursework.clickboardbackend.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    private String message;
    private LocalDateTime createdAt;
}
