package com.coursework.clickboardbackend.attribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeResponseDto {
    private int id;
    private String name;
    private String valueType;
}

