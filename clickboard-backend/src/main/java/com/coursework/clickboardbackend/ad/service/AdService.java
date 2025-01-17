package com.coursework.clickboardbackend.ad.service;

import com.coursework.clickboardbackend.CloudinaryService;
import com.coursework.clickboardbackend.ad.AdSpecification;
import com.coursework.clickboardbackend.ad.dto.AdRequestDto;
import com.coursework.clickboardbackend.ad.dto.AdResponseDto;
import com.coursework.clickboardbackend.ad.dto.SavedAdDto;
import com.coursework.clickboardbackend.ad.model.Ad;
import com.coursework.clickboardbackend.ad.model.AdAttribute;
import com.coursework.clickboardbackend.ad.model.AdPhoto;
import com.coursework.clickboardbackend.ad.model.SavedAd;
import com.coursework.clickboardbackend.ad.repostitory.AdAttributeRepository;
import com.coursework.clickboardbackend.ad.repostitory.AdPhotoRepository;
import com.coursework.clickboardbackend.ad.repostitory.AdRepository;
import com.coursework.clickboardbackend.ad.repostitory.SavedAdRepository;
import com.coursework.clickboardbackend.attribute.model.Attribute;
import com.coursework.clickboardbackend.attribute.repository.AttributeRepository;
import com.coursework.clickboardbackend.category.repository.CategoryRepository;
import com.coursework.clickboardbackend.notification.NotificationService;
import com.coursework.clickboardbackend.user.model.User;
import com.coursework.clickboardbackend.user.repository.UserRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final CategoryRepository categoryRepository;
    private final AdPhotoRepository adPhotoRepository;
    private final AttributeRepository attributeRepository;
    private final AdAttributeRepository adAttributeRepository;
    private final AdSpecification adSpecification;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final NotificationService notificationService;
    private final SavedAdRepository savedAdRepository;

    public AdService(AdRepository adRepository, CategoryRepository categoryRepository,
                     AdPhotoRepository adPhotoRepository, AttributeRepository attributeRepository, AdAttributeRepository adAttributeRepository, AdSpecification adSpecification, UserRepository userRepository, CloudinaryService cloudinaryService, NotificationService notificationService, SavedAdRepository savedAdRepository) {
        this.adRepository = adRepository;
        this.categoryRepository = categoryRepository;
        this.adPhotoRepository = adPhotoRepository;
        this.attributeRepository = attributeRepository;
        this.adAttributeRepository = adAttributeRepository;
        this.adSpecification = adSpecification;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.notificationService = notificationService;
        this.savedAdRepository = savedAdRepository;
    }

    @Transactional
    public AdResponseDto createAd(AdRequestDto requestDTO) {
        Ad ad = new Ad();
        ad.setPrice(requestDTO.getPrice());
        ad.setTitle(requestDTO.getTitle());
        ad.setDescription(requestDTO.getDescription());
        ad.setCategory(categoryRepository.findById(requestDTO.getCategoryId()).orElseThrow());
        ad.setStatus(Ad.Status.PENDING);
        ad.setCreatedAt(LocalDateTime.now());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Пользователь не аутентифицирован");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        // Найти пользователя в базе данных
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден"));

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        ad.setUser(user);

        ad = adRepository.save(ad);

        // Добавление атрибутов
        Ad finalAd = ad;
        requestDTO.getAttributes().forEach((key, value) -> {
            Attribute attribute = attributeRepository.findById(Integer.parseInt(key))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid attribute for category"));

            AdAttribute adAttribute = new AdAttribute();
            adAttribute.setAd(finalAd);
            adAttribute.setAttribute(attribute);
            adAttribute.setValue(value);

            adAttributeRepository.save(adAttribute);
        });

        List<String> photoUrls = new ArrayList<>();
        List<String> base64Photo = requestDTO.getPhotos();
        for (String s : base64Photo) {
            byte[] fileBytes = Base64.getDecoder().decode(s);
            try {
                // Сохраняем файл локально или загружаем в облако
                File file = new File("uploads/photo.jpg");
                FileUtils.writeByteArrayToFile(file, fileBytes);

                // Загрузка в Cloudinary
                String photoUrl = cloudinaryService.uploadImage(file);
                photoUrls.add(photoUrl);

                // Удаление временного файла
                file.delete();
            } catch (IOException e) {
            }
        }

        if (photoUrls.isEmpty()) {
            AdPhoto photo = new AdPhoto();
            photo.setPhotoUrl("https://res.cloudinary.com/dionvmulc/image/upload/v1734510457/9044950_no_image_icon_glykxw.png");
            photo.setAd(finalAd);
            adPhotoRepository.save(photo);
        }
        else {
            photoUrls.forEach(photoUrl -> {
                AdPhoto photo = new AdPhoto();
                photo.setPhotoUrl(photoUrl);
                photo.setAd(finalAd);
                adPhotoRepository.save(photo);
            });
        }

        updateAdStatus(ad.getId(), Ad.Status.PENDING);

        return mapToAdResponseDTO(ad);
    }

    public Page<AdResponseDto> getAds(String title, Integer categoryId, Integer minPrice, Integer maxPrice, Pageable pageable) {
        Specification<Ad> specification = Specification
                .where(adSpecification.titleStartsWith(title))
                .and(adSpecification.categoryEquals(categoryId))
                .and(adSpecification.priceGreaterThanOrEqual(minPrice))
                .and(adSpecification.priceLessThanOrEqual(maxPrice))
                .and(adSpecification.status(Ad.Status.APPROVED));

        return adRepository.findAll(specification, pageable).map(this::mapToAdResponseDTO);
    }

    public Page<AdResponseDto> getUserAds(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        // Получаем объявления пользователя с пагинацией
        Page<Ad> ads = adRepository.findByUserId(user.getId(), pageable);

        return ads.map(this::mapToAdResponseDTO);
    }

    public AdResponseDto getAdById(int id) {
        Ad ad = adRepository.findById(id).orElseThrow();
        return mapToAdResponseDTO(ad);
    }

    public Ad updateAdStatus(int id, Ad.Status status) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found with ID: " + id));

        ad.setStatus(status);
        adRepository.save(ad);

        // Создаем уведомление для пользователя
        String message = generateModerationMessage(status);
        notificationService.createNotification(ad.getUser(), ad, message);

        return ad;
    }

    private String generateModerationMessage(Ad.Status status) {
        return switch (status) {
            case APPROVED -> "Ваше объявление одобрено модератором.";
            case REJECTED -> "Ваше объявление отклонено модератором.";
            case BLOCKED -> "Ваше объявление заблокировано.";
            case PENDING -> "Ваше объявление отправлено на проверку";
            case ARCHIVED -> "Ваше объявление перенесено в архив";
            default -> "Статус вашего объявления изменен";
        };
    }

    private AdResponseDto mapToAdResponseDTO(Ad ad) {
        AdResponseDto dto = new AdResponseDto();

        dto.setId(ad.getId());
        dto.setUserId(ad.getUser().getId());
        dto.setTitle(ad.getTitle());
        dto.setDescription(ad.getDescription());
        dto.setPrice(ad.getPrice());
        dto.setStatus(ad.getStatus().name());
        dto.setCreatedAt(ad.getCreatedAt());
        dto.setCategoryName(ad.getCategory().getName());
        dto.setCategoryId(ad.getCategory().getId());

        List<String> photoUrls = adPhotoRepository.findByAdId(ad.getId())
                .stream()
                .map(AdPhoto::getPhotoUrl)
                .collect(Collectors.toList());
        dto.setPhotoUrls(photoUrls);

        Map<String, String> attributes = adAttributeRepository.findByAdId(ad.getId())
                .stream()
                .collect(Collectors.toMap(
                        adAttr -> adAttr.getAttribute().getName(),
                        AdAttribute::getValue
                ));
        dto.setAttributes(attributes);

        // Преобразование данных продавца
        dto.setSellerPhone(ad.getUser().getPhone());
        dto.setSellerFirstName(ad.getUser().getFirstname());
        dto.setSellerLastName(ad.getUser().getLastname());

        // Добавляем информацию о том, является ли текущий пользователь владельцем
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        dto.setOwnedByCurrentUser(currentUsername.equals(ad.getUser().getUsername()));

        // Устанавливаем поле isSaved
        boolean isSaved = savedAdRepository.existsByUserUsernameAndAdId(currentUsername, ad.getId());
        dto.setSaved(isSaved);

        return dto;
    }

    public List<AdResponseDto> getAdsByStatus(Ad.Status status) {
        return adRepository.findByStatus(status).stream().map(this::mapToAdResponseDTO).toList();
    }

    public void saveAd(String username, int adId) {
        var user = userRepository.findByUsername(username).orElseThrow();
        var ad = adRepository.findById(adId).orElseThrow();
        if (!ad.getUser().getUsername().equals(username)) {
            SavedAd savedAd = new SavedAd();
            savedAd.setUser(user);
            savedAd.setAd(ad);
            savedAdRepository.save(savedAd);
        } else {
            throw new IllegalArgumentException("Нельзя добавить своё объявление в избранное");
        }
    }

    @Transactional
    public void removeAd(String username, int adId) {
        var user = userRepository.findByUsername(username).orElseThrow();
        savedAdRepository.deleteByUserIdAndAdId(user.getId(), adId);
    }

    public List<SavedAdDto> getSavedAds(String username) {
        var user = userRepository.findByUsername(username).orElseThrow();
        List<SavedAd> savedAds = savedAdRepository.findByUserId(user.getId());

        return savedAds.stream().map(savedAd -> {
            var ad = savedAd.getAd();
            var dto = new SavedAdDto();
            dto.setId(savedAd.getId());
            dto.setAdId(ad.getId());
            dto.setTitle(ad.getTitle());
            dto.setDescription(ad.getDescription());
            dto.setPrice(ad.getPrice());
            dto.setCategoryName(ad.getCategory().getName());
            dto.setSellerFirstName(ad.getUser().getFirstname());
            dto.setSellerLastName(ad.getUser().getLastname());
            List<String> photoUrls = adPhotoRepository.findByAdId(ad.getId())
                    .stream()
                    .map(AdPhoto::getPhotoUrl)
                    .collect(Collectors.toList());
            dto.setPhotoUrls(photoUrls);
            return dto;
        }).toList();
    }

    @Transactional
    public AdResponseDto updateAd(int id, AdRequestDto requestDTO, String username) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Объявление не найдено"));

        if (!ad.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("Вы не являетесь владельцем этого объявления");
        }

        // Обновление основных данных объявления
        ad.setTitle(requestDTO.getTitle());
        ad.setDescription(requestDTO.getDescription());
        ad.setPrice(requestDTO.getPrice());
        ad.setCategory(categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена")));
        ad.setStatus(Ad.Status.PENDING); // Устанавливаем статус PENDING после обновления

        // Обновление атрибутов
        adAttributeRepository.deleteByAdId(ad.getId()); // Удаляем старые атрибуты
        Ad finalAd = ad;
        requestDTO.getAttributes().forEach((key, value) -> {
            Attribute attribute = attributeRepository.findById(Integer.parseInt(key))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid attribute for category"));

            AdAttribute adAttribute = new AdAttribute();
            adAttribute.setAd(finalAd);
            adAttribute.setAttribute(attribute);
            adAttribute.setValue(value);

            adAttributeRepository.save(adAttribute);
        });

        // Обновление фотографий, если переданы
        if (requestDTO.getPhotos() != null && !requestDTO.getPhotos().isEmpty()) {
            adPhotoRepository.deleteByAdId(ad.getId()); // Удаляем старые фотографии

            List<String> photoUrls = new ArrayList<>();
            List<String> base64Photo = requestDTO.getPhotos();
            for (String s : base64Photo) {
                byte[] fileBytes = Base64.getDecoder().decode(s);
                try {
                    // Сохраняем файл локально или загружаем в облако
                    File file = new File("uploads/photo.jpg");
                    FileUtils.writeByteArrayToFile(file, fileBytes);

                    // Загрузка в Cloudinary
                    String photoUrl = cloudinaryService.uploadImage(file);
                    photoUrls.add(photoUrl);

                    // Удаление временного файла
                    file.delete();
                } catch (IOException e) {
                    throw new IllegalStateException("Ошибка при загрузке изображения");
                }
            }

            photoUrls.forEach(photoUrl -> {
                AdPhoto photo = new AdPhoto();
                photo.setPhotoUrl(photoUrl);
                photo.setAd(finalAd);
                adPhotoRepository.save(photo);
            });
        }

        adRepository.save(ad);
        return mapToAdResponseDTO(ad);
    }
}

