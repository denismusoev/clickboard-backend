package com.coursework.clickboardbackend.ad.repostitory;

import com.coursework.clickboardbackend.ad.model.AdAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdAttributeRepository extends JpaRepository<AdAttribute, Integer> {
    List<AdAttribute> findByAdId(int adId);
}


