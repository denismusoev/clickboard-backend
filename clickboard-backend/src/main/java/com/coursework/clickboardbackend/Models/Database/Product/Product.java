package com.coursework.clickboardbackend.Models.Database.Ad;

import com.coursework.clickboardbackend.Models.DTO.Ad.AdNestedDTO;
import com.coursework.clickboardbackend.Models.DTO.Ad.AdViewDTO;
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
    private Category category;

    @OneToMany(mappedBy = "ad")
    private List<AdAttribute> adAttributes = new ArrayList<>();

    public Ad() {

    }

    public Ad(AdViewDTO adViewDTO) {
        this.id = adViewDTO.getId();
//        this.description = adViewDTO.getDescription();
//        this.rating = adViewDTO.getRating();
//        this.image = adViewDTO.getImage();
//        this.storeList = adViewDTO.getStoreList().stream().map(StoreItem::new).toList();
//        this.discountList = adViewDTO.getDiscountList().stream().map(Discount::new).toList();
//        this.categoryList = adViewDTO.getCategoryList().stream().map(Category::new).toList();
    }

    public Ad(AdNestedDTO adNestedDTO) {
        this.id = adNestedDTO.getId();
//        this.name = adNestedDTO.getName();
//        this.price = adNestedDTO.getId();
//        this.description = adNestedDTO.getDescription();
//        this.rating = adNestedDTO.getRating();
//        this.image = adNestedDTO.getImage();
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

    public Category getCategory() {
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

    //
//    public List<CartItem> getCartItems() {
//        return cartItems;
//    }
//
//    public void setCartItems(List<CartItem> cartItems) {
//        this.cartItems = cartItems;
//    }
//
//    public void addToCartItems(CartItem cartItem) {
//        this.cartItems.add(cartItem);
//    }
//
//    public void removeFromCartItems(CartItem cartItem) {
//        this.cartItems.removeIf(item -> item.getId() == cartItem.getId());
//    }
}
