package com.attractions.attractionsProject.controller;

import com.attractions.attractionsProject.model.Assistance;
import com.attractions.attractionsProject.model.Attraction;
import com.attractions.attractionsProject.repository.AssistanceRepository;
import com.attractions.attractionsProject.repository.AttractionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/assistance")
public class AssistanceController {

    private final AssistanceRepository assistanceRepository;
    private final AttractionRepository attractionRepository;

    public AssistanceController(
            AssistanceRepository assistanceRepository,
            AttractionRepository attractionRepository
    ) {
        this.assistanceRepository = assistanceRepository;
        this.attractionRepository = attractionRepository;
    }

    @GetMapping
    public ResponseEntity<List<Assistance>> getAllAssistance() {
        List<Assistance> assistance = new ArrayList<>(assistanceRepository.findAll());

        if (assistance.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(assistance, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assistance> getAssistanceById(@PathVariable(value = "id") Long id) {
        Assistance assistance = assistanceRepository.findById(id)
                .orElse(new Assistance());

        return new ResponseEntity<>(assistance, HttpStatus.OK);
    }

    @PostMapping("/attractions/{attractionId}")
    public ResponseEntity<Assistance> addAssistanceToAttraction(
            @PathVariable(value = "attractionId") Long attractionId,
            @RequestBody Assistance assistanceRequest
    ) {
        Assistance assistance = attractionRepository.findById(attractionId).map(attraction -> {
            Long assistanceId = assistanceRepository
                    .findByServiceTypeAndShortDescriptionContainingAndExecutorContaining(
                            assistanceRequest.getServiceType(),
                            assistanceRequest.getShortDescription(),
                            assistanceRequest.getExecutor()
                    ).stream()
                    .findFirst()
                    .map(Assistance::getId)
                    .orElse(0L);

            if (assistanceId != 0L) {
                Assistance _assistance = assistanceRepository.findById(assistanceId)
                        .orElse(new Assistance());
                attraction.addAssistance(_assistance);
                attractionRepository.save(attraction);
                return _assistance;
            }

            attraction.addAssistance(assistanceRequest);
            return assistanceRepository.save(assistanceRequest);
        }).orElse(new Assistance());

        return new ResponseEntity<>(assistance, HttpStatus.CREATED);
    }

    @DeleteMapping("/{assistanceId}/attractions/{attractionId}")
    public ResponseEntity<HttpStatus> deleteAssistanceFromAttraction(
            @PathVariable(value = "assistanceId") Long assistanceId,
            @PathVariable(value = "attractionId") Long attractionId
    ) {
        Attraction attraction = attractionRepository.findById(attractionId)
                .orElse(new Attraction());

        attraction.removeAssistance(assistanceId);
        attractionRepository.save(attraction);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAssistance(
            @PathVariable("id") Long id
    ) {
        assistanceRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
