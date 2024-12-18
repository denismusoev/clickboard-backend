package com.coursework.clickboardbackend.ad.repostitory;

import com.coursework.clickboardbackend.ad.model.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer>, JpaSpecificationExecutor<Ad> {
    List<Ad> findByStatus(Ad.Status status);
    @Query("SELECT a FROM Ad a WHERE a.user.id = :userId ORDER BY a.createdAt DESC")
    Page<Ad> findByUserId(@Param("userId") int userId, Pageable pageable);
    Optional<Ad> findById(int id);
}

