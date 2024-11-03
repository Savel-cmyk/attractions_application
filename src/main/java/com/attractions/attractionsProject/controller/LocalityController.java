package com.attractions.attractionsProject.controller;

import com.attractions.attractionsProject.dtos.LocalityDto;
import com.attractions.attractionsProject.service.LocalityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/localities")
public class LocalityController {

    private final LocalityService localityService;

    public LocalityController(
            LocalityService localityService
                              ) {
        this.localityService = localityService;
    }

    @PostMapping
    public ResponseEntity<LocalityDto> createLocality(@RequestBody @Valid LocalityDto localityDto) {

        LocalityDto localityResponseDto = localityService.createLocality(localityDto);

        if (localityResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(localityDto, HttpStatus.CREATED);
    }

}
