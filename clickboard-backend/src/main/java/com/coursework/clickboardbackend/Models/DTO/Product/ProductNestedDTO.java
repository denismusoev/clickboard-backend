package com.coursework.clickboardbackend.Models.DTO.Ad;

import com.coursework.clickboardbackend.Models.Database.Ad.Ad;

public class AdNestedDTO {
    private int id;
    private String name;
    private String description;
    private float rating;
    private String image;

    public AdNestedDTO() {
    }

    public AdNestedDTO(Ad ad) {
        this.id = ad.getId();
        this.name = ad.getName();
        this.description = ad.getDescription();
        this.rating = ad.getRating();
        this.image = ad.getImage();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }
}
