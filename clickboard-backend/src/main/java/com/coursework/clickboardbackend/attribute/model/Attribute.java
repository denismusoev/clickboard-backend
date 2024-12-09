package com.coursework.clickboardbackend.attribute.model;

import com.coursework.clickboardbackend.category.Category;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "attribute")
@Data
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;
    private String valueType;
}

