package com.coursework.clickboardbackend.ad.model;

import com.coursework.clickboardbackend.attribute.Attribute;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ad_attribute")
@Data
public class AdAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    private String value;
}

