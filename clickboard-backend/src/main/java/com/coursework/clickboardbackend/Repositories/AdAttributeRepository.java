package com.coursework.clickboardbackend.Repositories;

import com.coursework.clickboardbackend.Models.Database.Product.AdAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdAttributeRepository extends JpaRepository<AdAttribute, Integer> {
}
