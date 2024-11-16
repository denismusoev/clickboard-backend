package com.coursework.clickboardbackend.ad.model;

import com.coursework.clickboardbackend.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "saved_ad")
@Data
public class SavedAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;
}

