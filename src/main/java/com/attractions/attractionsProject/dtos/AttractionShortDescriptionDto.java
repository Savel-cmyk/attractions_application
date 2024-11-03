package com.attractions.attractionsProject.dtos;

import jakarta.validation.constraints.NotBlank;

public record AttractionShortDescriptionDto(
        @NotBlank(message = "Set new description for attraction or left it as it is.")
        String shortDescription
){
}
