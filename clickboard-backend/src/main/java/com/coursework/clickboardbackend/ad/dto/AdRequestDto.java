package com.coursework.clickboardbackend.ad.dto;

import lombok.Data;

import java.util.Map;

@Data
public class AdRequestDto {
    private String title;
    private String description;
    private double price;
    private int categoryId;
    private Map<String, String> attributes;
}

