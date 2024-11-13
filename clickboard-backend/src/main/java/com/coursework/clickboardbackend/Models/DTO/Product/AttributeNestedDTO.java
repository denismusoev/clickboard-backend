package com.coursework.clickboardbackend.Models.DTO.Ad;

import com.coursework.clickboardbackend.Models.Database.Ad.AdAttribute;

public class AttributeNestedDTO {
    private int id;
    private String name;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AttributeNestedDTO(AdAttribute adAttribute) {
        this.id = adAttribute.getAttribute().getId();
        this.name = adAttribute.getAttribute().getName();
        this.value = adAttribute.getValue();
    }

    public AttributeNestedDTO() {
    }
}
