package com.coursework.clickboardbackend.Repositories;

import com.coursework.clickboardbackend.Models.Database.Ad.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
}

