package com.coursework.clickboardbackend.ad.controller;

import com.coursework.clickboardbackend.ad.dto.AdRequestDto;
import com.coursework.clickboardbackend.ad.dto.AdResponseDto;
import com.coursework.clickboardbackend.ad.service.AdService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    @PostMapping
    public ResponseEntity<AdResponseDto> createAd(@RequestBody AdRequestDto requestDTO) {
        return ResponseEntity.ok(adService.createAd(requestDTO));
    }

    @GetMapping
    public ResponseEntity<Page<AdResponseDto>> getAds(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(adService.getAds(title, categoryId, minPrice, maxPrice, PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdResponseDto> getAdById(@PathVariable int id) {
        return ResponseEntity.ok(adService.getAdById(id));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Void> approveAd(@PathVariable int id) {
        adService.approveAd(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Void> rejectAd(@PathVariable int id) {
        adService.rejectAd(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<Void> blockAd(@PathVariable int id) {
        adService.blockAd(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<Void> archiveAd(@PathVariable int id) {
        adService.archiveAd(id);
        return ResponseEntity.ok().build();
    }
}

