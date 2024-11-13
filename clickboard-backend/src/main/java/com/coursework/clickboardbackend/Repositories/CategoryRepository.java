package com.coursework.clickboardbackend.Repositories;

import com.coursework.clickboardbackend.Models.Database.Ad.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
