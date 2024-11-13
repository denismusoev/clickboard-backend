package com.coursework.clickboardbackend.Models.DTO.Ad;

import com.coursework.clickboardbackend.Models.Database.Ad.Category;

public class CategoryCompositeDTO {
    private int id;
    private String name;
    private String description;

    public CategoryCompositeDTO(){

    }

    public CategoryCompositeDTO(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
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
}
