package com.coursework.clickboardbackend.ad.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ad_photo")
@Data
public class AdPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    private String photoUrl;
}

