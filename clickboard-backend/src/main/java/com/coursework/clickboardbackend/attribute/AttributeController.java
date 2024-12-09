package com.coursework.clickboardbackend.attribute;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attributes")
public class AttributeController {

    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<AttributeResponseDto>> getAttributesByCategory(@PathVariable int categoryId) {
        return ResponseEntity.ok(attributeService.getAttributesByCategory(categoryId));
    }
}

