package com.coursework.clickboardbackend.ad.controller;

import com.coursework.clickboardbackend.ad.dto.AdRequestDto;
import com.coursework.clickboardbackend.ad.dto.AdResponseDto;
import com.coursework.clickboardbackend.ad.model.Ad;
import com.coursework.clickboardbackend.ad.model.SavedAd;
import com.coursework.clickboardbackend.ad.service.AdService;
import com.coursework.clickboardbackend.user.model.User;
import com.coursework.clickboardbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.GrantedAuthority;


import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<AdResponseDto> createAd(@RequestBody AdRequestDto requestDTO) {
        log.info("Данные объявления: {}", requestDTO);
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

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdResponseDto>> getPendingAds() {
        List<AdResponseDto> pendingAds = adService.getAdsByStatus(Ad.Status.PENDING);
        return ResponseEntity.ok(pendingAds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdResponseDto> getAdById(@PathVariable int id) throws Exception {
        AdResponseDto ad = adService.getAdById(id);

        // Получаем текущего пользователя
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        // Проверяем роль пользователя
        String role = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        // Проверяем, является ли пользователь владельцем объявления или администратором
        User user = userService.getByUsername(currentUsername);
        boolean isOwner = ad.getUserId() == user.getId();

        if (!role.equals("ROLE_ADMIN") && !isOwner && Ad.Status.valueOf(ad.getStatus()) != Ad.Status.APPROVED) {
            throw new AccessDeniedException("Доступ запрещен");
        }

        return ResponseEntity.ok(ad);
    }

    @PostMapping("/test-notification")
    public void testNotification(@RequestParam String username) {
        System.out.println("Sending to user: " + username);
        messagingTemplate.convertAndSendToUser(username, "/queue/messages", "Test message");
        System.out.println("Message sent to user: " + username);
    }

    @PostMapping("/{id}/moderate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> moderateAd(
            @PathVariable int id,
            @RequestParam Ad.Status status) {
        adService.updateAdStatus(id, status);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}/archive")
    public ResponseEntity<Void> archiveAd(@PathVariable int id) {
        adService.updateAdStatus(id, Ad.Status.ARCHIVED);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-ads")
    public ResponseEntity<Page<AdResponseDto>> getUserAds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<AdResponseDto> userAds = adService.getUserAds(username, PageRequest.of(page, size));
        return ResponseEntity.ok(userAds);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdResponseDto> updateAd(
            @PathVariable int id,
            @RequestBody AdRequestDto requestDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        AdResponseDto updatedAd = adService.updateAd(id, requestDTO, username);
        return ResponseEntity.ok(updatedAd);
    }
}

