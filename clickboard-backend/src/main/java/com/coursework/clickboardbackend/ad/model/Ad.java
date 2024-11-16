package com.coursework.clickboardbackend.ad.model;

import com.coursework.clickboardbackend.category.Category;
import com.coursework.clickboardbackend.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ad")
@Data
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AdAttribute> adAttributes;

    private String title;
    private String description;

    @Column(nullable = true)
    private double price;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    public enum Status {
        PENDING, APPROVED, REJECTED, BLOCKED, ARCHIVED
    }
}