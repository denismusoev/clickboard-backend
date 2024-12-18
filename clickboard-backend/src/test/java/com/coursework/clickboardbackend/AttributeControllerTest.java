package com.coursework.clickboardbackend;

import com.coursework.clickboardbackend.attribute.AttributeController;
import com.coursework.clickboardbackend.attribute.AttributeResponseDto;
import com.coursework.clickboardbackend.attribute.AttributeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AttributeControllerTest {

    @InjectMocks
    private AttributeController attributeController;

    @Mock
    private AttributeService attributeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAttributesByCategory() {
        // Arrange
        int categoryId = 1;
        List<AttributeResponseDto> attributes = List.of(new AttributeResponseDto());
        when(attributeService.getAttributesByCategory(categoryId)).thenReturn(attributes);

        // Act
        ResponseEntity<List<AttributeResponseDto>> response = attributeController.getAttributesByCategory(categoryId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(attributes, response.getBody());
        verify(attributeService, times(1)).getAttributesByCategory(categoryId);
    }
}
