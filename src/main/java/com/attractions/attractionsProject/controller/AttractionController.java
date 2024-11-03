package com.attractions.attractionsProject.controller;

import com.attractions.attractionsProject.dtos.*;
import com.attractions.attractionsProject.service.AttractionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    public AttractionController(
            AttractionService attractionService
    ) {
        this.attractionService = attractionService;
    }

    @GetMapping
    public ResponseEntity<List<AttractionResponseDto>> getAllAttractions(
            @RequestParam(required = false)
            @Pattern(regexp = "(?i)^(natural|cultural|architectural|entertainment|wildlife)$",
                    message = "Given type should be from next list of strings: natural, cultural, " +
                            "architectural, entertainment, wildlife.") String filterType,
            @RequestParam(required = false)
            @Pattern(regexp = "(?i)^(asc|desc)$",
                    message = "Given type should be either desc or asc.") String sortType
    ) {
        List<AttractionResponseDto> attractions = attractionService.getAllAttractions(filterType, sortType);

        if (attractions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @GetMapping("/locality")
    public ResponseEntity<List<AttractionDto>> getAllAttractionsByLocality(
            @RequestBody @Valid LocalityDto localityDto
    ) {
        List<AttractionDto> attractions = attractionService.getAllAttractionsByLocality(localityDto);

        if (attractions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @PostMapping("/localities/{localityId}")
    public ResponseEntity<AttractionResponseDto> addAttractionToLocality(
            @PathVariable(value = "localityId") Long localityId,
            @RequestBody @Valid AttractionDto attractionRequest
    ) {
        AttractionResponseDto attraction = attractionService.addAttractionToLocality(localityId, attractionRequest);

        return new ResponseEntity<>(attraction, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttractionResponseDto> updateAttractionShortDescription(
            @PathVariable("id") Long attractionId,
            @RequestBody AttractionShortDescriptionDto shortDescription
    ) {
        AttractionResponseDto attraction =
                attractionService.updateAttractionShortDescription(attractionId, shortDescription);

        return new ResponseEntity<>(attraction, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAttraction(@PathVariable("id") Long id) {

        attractionService.deleteAttraction(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
