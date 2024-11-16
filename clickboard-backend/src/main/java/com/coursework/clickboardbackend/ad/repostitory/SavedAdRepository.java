package com.coursework.clickboardbackend.ad.repostitory;

import com.coursework.clickboardbackend.ad.model.SavedAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedAdRepository extends JpaRepository<SavedAd, Integer> {
}

