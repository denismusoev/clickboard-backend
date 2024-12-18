package com.coursework.clickboardbackend;

import com.coursework.clickboardbackend.ad.controller.AdController;
import com.coursework.clickboardbackend.ad.dto.AdRequestDto;
import com.coursework.clickboardbackend.ad.dto.AdResponseDto;
import com.coursework.clickboardbackend.ad.model.Ad;
import com.coursework.clickboardbackend.ad.service.AdService;
import com.coursework.clickboardbackend.user.model.User;
import com.coursework.clickboardbackend.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AdControllerTest {

    @InjectMocks
    private AdController adController;

    @Mock
    private AdService adService;

    @Mock
    private UserService userService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testCreateAd() {
        // Arrange
        AdRequestDto requestDto = new AdRequestDto();
        AdResponseDto responseDto = new AdResponseDto();
        when(adService.createAd(any(AdRequestDto.class))).thenReturn(responseDto);

        // Act
        ResponseEntity<AdResponseDto> response = adController.createAd(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(responseDto, response.getBody());
        verify(adService, times(1)).createAd(any(AdRequestDto.class));
    }

    @Test
    void testGetAds() {
        // Arrange
        Page<AdResponseDto> page = new PageImpl<>(Collections.emptyList());
        when(adService.getAds(any(), any(), any(), any(), any(PageRequest.class))).thenReturn(page);

        // Act
        ResponseEntity<Page<AdResponseDto>> response = adController.getAds(null, null, null, null, 0, 10);

        // Assert
        assertNotNull(response);
        assertEquals(page, response.getBody());
        verify(adService, times(1)).getAds(any(), any(), any(), any(), any(PageRequest.class));
    }

    @Test
    void testGetPendingAds() {
        // Arrange
        List<AdResponseDto> pendingAds = List.of(new AdResponseDto());
        when(adService.getAdsByStatus(Ad.Status.PENDING)).thenReturn(pendingAds);

        // Act
        ResponseEntity<List<AdResponseDto>> response = adController.getPendingAds();

        // Assert
        assertNotNull(response);
        assertEquals(pendingAds, response.getBody());
        verify(adService, times(1)).getAdsByStatus(Ad.Status.PENDING);
    }

    @Test
    void testGetAdById_AsAdminOrOwner() throws Exception {
        // Arrange
        int adId = 1;
        AdResponseDto adResponse = new AdResponseDto();
        adResponse.setUserId(1);
        adResponse.setStatus("APPROVED");
        User user = new User();
        user.setId(1);

        when(authentication.getName()).thenReturn("user");
        when(userService.getByUsername("user")).thenReturn(user);
        when(adService.getAdById(adId)).thenReturn(adResponse);

        // Act
        ResponseEntity<AdResponseDto> response = adController.getAdById(adId);

        // Assert
        assertNotNull(response);
        assertEquals(adResponse, response.getBody());
        verify(adService, times(1)).getAdById(adId);
    }

    @Test
    void testGetAdById_AccessDenied() throws Exception {
        // Arrange
        int adId = 1;
        AdResponseDto adResponse = new AdResponseDto();
        adResponse.setUserId(2);
        adResponse.setStatus("PENDING");
        User user = new User();
        user.setId(1);

        when(authentication.getName()).thenReturn("user");
        when(userService.getByUsername("user")).thenReturn(user);
        when(adService.getAdById(adId)).thenReturn(adResponse);

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> adController.getAdById(adId));
    }

    @Test
    void testTestNotification() {
        // Arrange
        String username = "testUser";

        // Act
        adController.testNotification(username);

        // Assert
        verify(messagingTemplate, times(1)).convertAndSendToUser(eq(username), eq("/queue/messages"), eq("Test message"));
    }

    @Test
    void testModerateAd_ShouldUpdateAdStatus() {
        // Arrange
        int adId = 1;
        Ad.Status status = Ad.Status.APPROVED;
        Ad ad = new Ad();
        ad.setId(adId);
        ad.setStatus(status);

        // Мокируем поведение adService
        when(adService.updateAdStatus(eq(adId), eq(status))).thenReturn(ad);

        // Act
        ResponseEntity<Void> response = adController.moderateAd(adId, status);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(adService).updateAdStatus(adId, status);
    }



    @Test
    void testArchiveAd_ShouldArchiveAd() {
        // Arrange
        int adId = 1;
        Ad.Status status = Ad.Status.ARCHIVED;
        Ad ad = new Ad();
        ad.setId(adId);
        ad.setStatus(status);

        // Мокируем поведение adService
        when(adService.updateAdStatus(eq(adId), eq(status))).thenReturn(ad);

        // Act
        ResponseEntity<Void> response = adController.archiveAd(adId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(adService).updateAdStatus(adId, status);
    }


    @Test
    void testGetUserAds() {
        // Arrange
        Page<AdResponseDto> page = new PageImpl<>(Collections.emptyList());
        when(authentication.getName()).thenReturn("user");
        when(adService.getUserAds("user", PageRequest.of(0, 10))).thenReturn(page);

        // Act
        ResponseEntity<Page<AdResponseDto>> response = adController.getUserAds(0, 10);

        // Assert
        assertNotNull(response);
        assertEquals(page, response.getBody());
        verify(adService, times(1)).getUserAds("user", PageRequest.of(0, 10));
    }

    @Test
    void testUpdateAd() {
        // Arrange
        int adId = 1;
        AdRequestDto requestDto = new AdRequestDto();
        AdResponseDto responseDto = new AdResponseDto();
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user");
        when(adService.updateAd(eq(adId), any(AdRequestDto.class), eq("user"))).thenReturn(responseDto);

        // Act
        ResponseEntity<AdResponseDto> response = adController.updateAd(adId, requestDto, userDetails);

        // Assert
        assertNotNull(response);
        assertEquals(responseDto, response.getBody());
        verify(adService, times(1)).updateAd(eq(adId), any(AdRequestDto.class), eq("user"));
    }
}
