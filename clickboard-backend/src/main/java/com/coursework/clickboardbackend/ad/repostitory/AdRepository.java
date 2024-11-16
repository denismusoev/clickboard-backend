package com.coursework.clickboardbackend.ad.repostitory;

import com.coursework.clickboardbackend.ad.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer>, JpaSpecificationExecutor<Ad> {
}

