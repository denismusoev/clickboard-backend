package com.coursework.clickboardbackend.ad.controller;

import com.coursework.clickboardbackend.ad.dto.SavedAdDto;
import com.coursework.clickboardbackend.ad.model.SavedAd;
import com.coursework.clickboardbackend.ad.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-ads")
@RequiredArgsConstructor
public class SavedAdController {

    private final AdService savedAdService;

    @PostMapping("/{adId}")
    public ResponseEntity<Void> saveAd(@PathVariable int adId, @AuthenticationPrincipal UserDetails userDetails) {
        savedAdService.saveAd(userDetails.getUsername(), adId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{adId}")
    public ResponseEntity<Void> removeAd(@PathVariable int adId, @AuthenticationPrincipal UserDetails userDetails) {
        savedAdService.removeAd(userDetails.getUsername(), adId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<SavedAdDto>> getSavedAds(@AuthenticationPrincipal UserDetails userDetails) {
        List<SavedAdDto> savedAds = savedAdService.getSavedAds(userDetails.getUsername());
        return ResponseEntity.ok(savedAds);
    }
}
