package com.coursework.clickboardbackend;

import com.coursework.clickboardbackend.ad.dto.AdRequestDto;
import com.coursework.clickboardbackend.ad.dto.AdResponseDto;
import com.coursework.clickboardbackend.ad.service.AdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdService adService;

    @Test
    public void testCreateAd() throws Exception {
        AdRequestDto request = new AdRequestDto();
        request.setTitle("Test Ad");
        request.setDescription("Test Description");
        request.setPrice(100.0);
        request.setCategoryId(1);
        request.setAttributes(Map.of("color", "red"));

        AdResponseDto response = new AdResponseDto();
        response.setId(1);
        response.setUserId(1);
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        response.setPrice(request.getPrice());
        response.setStatus("ACTIVE");
        response.setCreatedAt(LocalDateTime.now());
        response.setCategoryId(request.getCategoryId());
        response.setPhotoUrls(List.of("url1", "url2"));
        response.setAttributes(request.getAttributes());

        Mockito.when(adService.createAd(Mockito.any(AdRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/ads")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.title").value(response.getTitle()))
                .andExpect(jsonPath("$.price").value(response.getPrice()));
    }

    @Test
    public void testGetAds() throws Exception {
        AdResponseDto response = new AdResponseDto();
        response.setId(1);
        response.setUserId(1);
        response.setTitle("Test Ad");
        response.setDescription("Test Description");
        response.setPrice(100.0);
        response.setStatus("ACTIVE");
        response.setCreatedAt(LocalDateTime.now());
        response.setCategoryId(1);
        response.setPhotoUrls(List.of("url1", "url2"));
        response.setAttributes(Map.of("color", "red"));

        Page<AdResponseDto> pageResponse = new PageImpl<>(List.of(response));
        Mockito.when(adService.getAds(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(Pageable.class)))
                .thenReturn(pageResponse);

        mockMvc.perform(get("/ads")
                        .param("title", "Test")
                        .param("categoryId", "1")
                        .param("minPrice", "50")
                        .param("maxPrice", "200")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(response.getId()))
                .andExpect(jsonPath("$.content[0].title").value(response.getTitle()))
                .andExpect(jsonPath("$.content[0].price").value(response.getPrice()));
    }

    @Test
    public void testGetAdById() throws Exception {
        AdResponseDto response = new AdResponseDto();
        response.setId(1);
        response.setUserId(1);
        response.setTitle("Test Ad");
        response.setDescription("Test Description");
        response.setPrice(100.0);
        response.setStatus("ACTIVE");
        response.setCreatedAt(LocalDateTime.now());
        response.setCategoryId(1);
        response.setPhotoUrls(List.of("url1", "url2"));
        response.setAttributes(Map.of("color", "red"));

        Mockito.when(adService.getAdById(1)).thenReturn(response);

        mockMvc.perform(get("/ads/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.title").value(response.getTitle()));
    }

//    @Test
//    public void testApproveAd() throws Exception {
//        mockMvc.perform(put("/ads/1/approve"))
//                .andExpect(status().isOk());
//
//        Mockito.verify(adService, Mockito.times(1)).approveAd(1);
//    }
//
//    @Test
//    public void testRejectAd() throws Exception {
//        mockMvc.perform(put("/ads/1/reject"))
//                .andExpect(status().isOk());
//
//        Mockito.verify(adService, Mockito.times(1)).rejectAd(1);
//    }
//
//    @Test
//    public void testBlockAd() throws Exception {
//        mockMvc.perform(put("/ads/1/block"))
//                .andExpect(status().isOk());
//
//        Mockito.verify(adService, Mockito.times(1)).blockAd(1);
//    }
//
//    @Test
//    public void testArchiveAd() throws Exception {
//        mockMvc.perform(put("/ads/1/archive"))
//                .andExpect(status().isOk());
//
//        Mockito.verify(adService, Mockito.times(1)).archiveAd(1);
//    }
}