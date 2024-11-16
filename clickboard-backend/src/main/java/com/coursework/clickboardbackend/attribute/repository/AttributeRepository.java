package com.coursework.clickboardbackend.attribute.repository;

import com.coursework.clickboardbackend.attribute.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
    Optional<Attribute> findByNameAndCategoryId(String name, int categoryId);
}

