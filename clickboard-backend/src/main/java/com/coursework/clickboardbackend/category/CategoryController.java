package com.coursework.clickboardbackend.category;

import com.coursework.clickboardbackend.ad.dto.AdRequestDto;
import com.coursework.clickboardbackend.ad.dto.AdResponseDto;
import com.coursework.clickboardbackend.ad.service.AdService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {

        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}


