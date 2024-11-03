package com.attractions.attractionsProject.dtos;

import jakarta.validation.constraints.NotBlank;

public record LocalityDto(
        @NotBlank(message = "Village or city localities that you're trying to add should have a name.")
        String settlement,
        @NotBlank(message = "Region of locality that you're trying to add should have a name.")
        String region
) {
}
