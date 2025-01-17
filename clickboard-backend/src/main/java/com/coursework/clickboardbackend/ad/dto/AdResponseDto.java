package com.coursework.clickboardbackend.ad.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class AdResponseDto {
    private int id;
    private int userId;
    private String title;
    private String description;
    private double price;
    private String status;
    private LocalDateTime createdAt;
    private String categoryName;
    private int categoryId;
    private List<String> photoUrls;
    private Map<String, String> attributes;

    private String sellerPhone;
    private String sellerFirstName;
    private String sellerLastName;
    private boolean isOwnedByCurrentUser;
    private boolean isSaved;
}

