package com.attractions.attractionsProject.dtos;

import com.attractions.attractionsProject.model.AttractionType;

public record AttractionResponseDto(
        String name,
        String creationDate,
        String shortDescription,
        AttractionType attractionType,
        LocalityDto locality
) {
}
