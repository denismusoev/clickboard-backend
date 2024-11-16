package com.coursework.clickboardbackend.category.repository;

import com.coursework.clickboardbackend.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
