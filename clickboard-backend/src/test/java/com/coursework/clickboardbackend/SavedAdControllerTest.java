package com.coursework.clickboardbackend;

import com.coursework.clickboardbackend.ad.controller.SavedAdController;
import com.coursework.clickboardbackend.ad.dto.SavedAdDto;
import com.coursework.clickboardbackend.ad.service.AdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SavedAdControllerTest {

    @InjectMocks
    private SavedAdController savedAdController;

    @Mock
    private AdService savedAdService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(userDetails.getUsername()).thenReturn("testUser");
    }

    @Test
    void testSaveAd() {
        // Arrange
        int adId = 1;
        doNothing().when(savedAdService).saveAd("testUser", adId);

        // Act
        ResponseEntity<Void> response = savedAdController.saveAd(adId, userDetails);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(savedAdService, times(1)).saveAd("testUser", adId);
    }

    @Test
    void testRemoveAd() {
        // Arrange
        int adId = 1;
        doNothing().when(savedAdService).removeAd("testUser", adId);

        // Act
        ResponseEntity<Void> response = savedAdController.removeAd(adId, userDetails);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(savedAdService, times(1)).removeAd("testUser", adId);
    }

    @Test
    void testGetSavedAds() {
        // Arrange
        List<SavedAdDto> savedAds = List.of(new SavedAdDto());
        when(savedAdService.getSavedAds("testUser")).thenReturn(savedAds);

        // Act
        ResponseEntity<List<SavedAdDto>> response = savedAdController.getSavedAds(userDetails);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedAds, response.getBody());
        verify(savedAdService, times(1)).getSavedAds("testUser");
    }
}
