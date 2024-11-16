package com.coursework.clickboardbackend.ad.repostitory;

import com.coursework.clickboardbackend.ad.model.AdPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdPhotoRepository extends JpaRepository<AdPhoto, Integer> {
    List<AdPhoto> findByAdId(int adId);
}

