package com.coursework.clickboardbackend.ad.repostitory;

import com.coursework.clickboardbackend.ad.model.SavedAd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedAdRepository extends JpaRepository<SavedAd, Integer> {
    List<SavedAd> findByUserId(int userId);
    void deleteByUserIdAndAdId(int userId, int adId);
    boolean existsByUserUsernameAndAdId(String username, int adId);
}


