package com.attractions.attractionsProject.dtos;

import com.attractions.attractionsProject.annotations.ValidDateFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AttractionDto(
        @NotBlank(message = "The attraction that you're trying to add considered to have a name.")
        String name,
        /*@PastOrPresent(message = "You cannot add an attraction that hasn't been built yet. Or at least you can left " +
                "this field blank.")*/
        @ValidDateFormat
        String creationDate,
        @NotNull
        String shortDescription,
        @NotBlank(message = "The attraction that you're trying to add considered to have a type.")
        @Pattern(regexp = "(?i)^(natural|cultural|architectural|entertainment|wildlife)$",
                message = "Given type should be from next list of strings (case-insensitive): natural, cultural, " +
                        "architectural, entertainment, wildlife.")
        String attractionType
) {

        public String replaceDateEraWithWithMinusIfBC() {

                if (creationDate.startsWith("BC", 11)) {

                        int year = Integer.parseInt(creationDate.substring(0, 4));
                        return "-" + --year + creationDate.substring(4, 10);
                }
                return creationDate.substring(0, 10);
        }
}
