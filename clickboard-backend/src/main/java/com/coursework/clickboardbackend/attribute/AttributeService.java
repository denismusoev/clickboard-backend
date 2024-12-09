package com.coursework.clickboardbackend.attribute;

import com.coursework.clickboardbackend.attribute.model.Attribute;
import com.coursework.clickboardbackend.attribute.repository.AttributeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeService {

    private final AttributeRepository attributeRepository;

    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public List<AttributeResponseDto> getAttributesByCategory(int categoryId) {
        List<Attribute> attributes = attributeRepository.findByCategoryId(categoryId);
        return attributes.stream()
                .map(attr -> new AttributeResponseDto(attr.getId(), attr.getName(), attr.getValueType()))
                .toList();
    }
}

