package com.coursework.clickboardbackend.Repositories;

import com.coursework.clickboardbackend.Models.Database.Ad.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdRepository extends JpaRepository<Ad, Integer> {
    Page<Ad> findByCategory_Id(int categoryId, Pageable pageable);
    @Query("SELECT p FROM Ad p WHERE p.category.id = :categoryId AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Ad> findByCategoryAndNameContainingIgnoreCase(@Param("categoryId") int categoryId, @Param("name") String name, Pageable pageable);
}

