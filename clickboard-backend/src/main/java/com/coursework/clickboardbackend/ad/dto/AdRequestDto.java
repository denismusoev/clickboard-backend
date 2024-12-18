package com.coursework.clickboardbackend.ad.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Data
public class AdRequestDto {
    private String title;
    private String description;
    private double price;
    private int categoryId;
    private Map<String, String> attributes;
    private List<String> photos;
    private List<String> currentPhotos;
}

