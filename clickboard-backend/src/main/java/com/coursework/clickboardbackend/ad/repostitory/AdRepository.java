package com.coursework.clickboardbackend.ad.repostitory;

import com.coursework.clickboardbackend.ad.model.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer>, JpaSpecificationExecutor<Ad> {
    List<Ad> findByStatus(Ad.Status status);
    Page<Ad> findByUserId(int userId, Pageable pageable);
}

