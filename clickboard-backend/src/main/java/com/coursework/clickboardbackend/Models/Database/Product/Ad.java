package com.coursework.clickboardbackend.Models.Database.Product;

import com.coursework.clickboardbackend.Models.DTO.Product.AdNestedDTO;
import com.coursework.clickboardbackend.Models.DTO.Product.AdViewDTO;
import com.coursework.clickboardbackend.Models.Database.Ad.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "price")
    @NotNull
    private float price;

    @Column(name = "description", columnDefinition = "TEXT")
    @NotNull
    private String description;

    @Column(name = "rating")
    @NotNull
    private float rating;

    @Column(name = "image", nullable = true)
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private com.coursework.clickboardbackend.Models.Database.Ad.Category category;

    @OneToMany(mappedBy = "ad")
    private List<AdAttribute> adAttributes = new ArrayList<>();

    public Ad() {

    }

    public Ad(AdViewDTO adViewDTO) {
        this.id = adViewDTO.getId();
    }

    public Ad(AdNestedDTO adNestedDTO) {
        this.id = adNestedDTO.getId();
    }

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public com.coursework.clickboardbackend.Models.Database.Ad.Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<AdAttribute> getAdAttributes() {
        return adAttributes;
    }

    public void setAdAttributes(List<AdAttribute> adAttributes) {
        this.adAttributes = adAttributes;
    }
}
