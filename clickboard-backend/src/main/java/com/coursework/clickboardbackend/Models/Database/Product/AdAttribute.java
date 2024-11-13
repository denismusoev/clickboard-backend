package com.coursework.clickboardbackend.Models.Database.Product;

import com.coursework.clickboardbackend.Models.Database.Product.Ad;
import com.coursework.clickboardbackend.Models.Database.Ad.Attribute;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ad_attributes")
public class AdAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    @Column(name = "value")
    @NotNull
    private String value;
}
