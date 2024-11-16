package com.coursework.clickboardbackend.ad.service;

import com.coursework.clickboardbackend.ad.AdSpecification;
import com.coursework.clickboardbackend.ad.dto.AdRequestDto;
import com.coursework.clickboardbackend.ad.dto.AdResponseDto;
import com.coursework.clickboardbackend.ad.model.Ad;
import com.coursework.clickboardbackend.ad.model.AdAttribute;
import com.coursework.clickboardbackend.ad.model.AdPhoto;
import com.coursework.clickboardbackend.ad.repostitory.AdAttributeRepository;
import com.coursework.clickboardbackend.ad.repostitory.AdPhotoRepository;
import com.coursework.clickboardbackend.ad.repostitory.AdRepository;
import com.coursework.clickboardbackend.attribute.Attribute;
import com.coursework.clickboardbackend.attribute.repository.AttributeRepository;
import com.coursework.clickboardbackend.category.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public AdService(AdRepository adRepository, CategoryRepository categoryRepository,
                     AdPhotoRepository adPhotoRepository, AttributeRepository attributeRepository, AdAttributeRepository adAttributeRepository, AdSpecification adSpecification) {
        this.adRepository = adRepository;
        this.categoryRepository = categoryRepository;
        this.adPhotoRepository = adPhotoRepository;
        this.attributeRepository = attributeRepository;
        this.adAttributeRepository = adAttributeRepository;
        this.adSpecification = adSpecification;
    }

    @Transactional
    public AdResponseDto createAd(AdRequestDto requestDTO) {
        Ad ad = new Ad();
        ad.setTitle(requestDTO.getTitle());
        ad.setDescription(requestDTO.getDescription());
        ad.setCategory(categoryRepository.findById(requestDTO.getCategoryId()).orElseThrow());
        ad.setStatus(Ad.Status.PENDING);
        ad = adRepository.save(ad);

        // Добавление атрибутов
        Ad finalAd = ad;
        requestDTO.getAttributes().forEach((key, value) -> {
            Attribute attribute = attributeRepository.findByNameAndCategoryId(key, finalAd.getCategory().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid attribute for category"));

            AdAttribute adAttribute = new AdAttribute();
            adAttribute.setAd(finalAd);
            adAttribute.setAttribute(attribute);
            adAttribute.setValue(value);

            adAttributeRepository.save(adAttribute);
        });

        return mapToAdResponseDTO(ad);
    }

    public Page<AdResponseDto> getAds(String title, Integer categoryId, Pageable pageable) {
        Specification<Ad> specification = Specification
                .where(adSpecification.titleContains(title))
                .and(adSpecification.categoryEquals(categoryId));

        return adRepository.findAll(specification, pageable).map(this::mapToAdResponseDTO);
    }

    public AdResponseDto getAdById(int id) {
        Ad ad = adRepository.findById(id).orElseThrow();
        return mapToAdResponseDTO(ad);
    }

    public void approveAd(int id) {
        Ad ad = updateAdStatus(id, Ad.Status.APPROVED);
    }

    public void rejectAd(int id) {
        Ad ad = updateAdStatus(id, Ad.Status.REJECTED);
    }

    public void blockAd(int id) {
        Ad ad = updateAdStatus(id, Ad.Status.BLOCKED);
    }

    public void archiveAd(int id) {
        Ad ad = updateAdStatus(id, Ad.Status.ARCHIVED);
    }

    private Ad updateAdStatus(int id, Ad.Status status) {
        Ad ad = adRepository.findById(id).orElseThrow();
        ad.setStatus(status);
        return adRepository.save(ad);
    }

    private AdResponseDto mapToAdResponseDTO(Ad ad) {
        AdResponseDto dto = new AdResponseDto();

        dto.setId(ad.getId());
        dto.setTitle(ad.getTitle());
        dto.setDescription(ad.getDescription());
        dto.setPrice(ad.getPrice());
        dto.setStatus(ad.getStatus().name());
        dto.setCreatedAt(ad.getCreatedAt());
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

        return dto;
    }
}

