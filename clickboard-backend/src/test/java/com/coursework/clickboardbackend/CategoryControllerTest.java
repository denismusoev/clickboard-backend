package com.coursework.clickboardbackend;

import com.coursework.clickboardbackend.category.CategoryController;
import com.coursework.clickboardbackend.category.CategoryResponseDto;
import com.coursework.clickboardbackend.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        List<CategoryResponseDto> categories = List.of(new CategoryResponseDto());
        when(categoryService.getAllCategories()).thenReturn(categories);

        // Act
        ResponseEntity<List<CategoryResponseDto>> response = categoryController.getAllCategories();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(categories, response.getBody());
        verify(categoryService, times(1)).getAllCategories();
    }
}
