package com.coursework.clickboardbackend.Models.DTO.Product;
import com.coursework.clickboardbackend.Models.DTO.Ad.CategoryCompositeDTO;
import com.coursework.clickboardbackend.Models.Database.Product.Ad;
import com.coursework.clickboardbackend.Models.DTO.Ad.CategoryCompositeDTO;

import java.util.List;

public class AdViewDTO {
    private int id;
    private String name;
    private double price;
    private String description;
    private float rating;
    private String image;
    private CategoryCompositeDTO category;
    private List<AttributeNestedDTO> attributeList;

    public AdViewDTO() {
    }

    public AdViewDTO(Ad ad) {
        this.id = ad.getId();
        this.name = ad.getName();
        this.price = ad.getPrice();
        this.description = ad.getDescription();
        this.rating = ad.getRating();
        this.image = ad.getImage();
        this.category = new CategoryCompositeDTO(ad.getCategory());
        this.attributeList = ad.getAdAttributes().stream().map(AttributeNestedDTO::new).toList();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
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

    public CategoryCompositeDTO getCategory() {
        return category;
    }

    public List<AttributeNestedDTO> getAttributeList() {
        return attributeList;
    }
}
