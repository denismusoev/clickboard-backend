package com.coursework.clickboardbackend.attribute;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttributeResponseDto {
    private int id;
    private String name;
    private String valueType;
}

