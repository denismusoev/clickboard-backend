package com.coursework.clickboardbackend.ad.dto;

import lombok.Data;

import java.util.List;

@Data
public class SavedAdDto {
    private int id; // ID записи в SavedAd
    private int adId; // ID объявления
    private String title; // Заголовок объявления
    private String description; // Описание объявления
    private List<String> photoUrls; // Описание объявления
    private double price; // Цена объявления
    private String categoryName; // Название категории
    private String sellerFirstName; // Имя продавца
    private String sellerLastName; // Фамилия продавца
}

