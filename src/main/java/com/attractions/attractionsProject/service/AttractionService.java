package com.attractions.attractionsProject.service;

import com.attractions.attractionsProject.dtos.*;
import com.attractions.attractionsProject.exception.InvalidDateFormatException;
import com.attractions.attractionsProject.exception.NotExistingParameterException;
import com.attractions.attractionsProject.mappers.AttractionMapper;
import com.attractions.attractionsProject.model.Attraction;
import com.attractions.attractionsProject.model.AttractionType;
import com.attractions.attractionsProject.repository.AttractionRepository;
import com.attractions.attractionsProject.repository.LocalityRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final AttractionMapper attractionMapper;
    private final LocalityRepository localityRepository;

    public AttractionService(
            AttractionRepository attractionRepository,
            AttractionMapper attractionMapper,
            LocalityRepository localityRepository
    ) {
        this.attractionRepository = attractionRepository;
        this.attractionMapper = attractionMapper;
        this.localityRepository = localityRepository;
    }

    public List<AttractionResponseDto> getAllAttractions(String filterType, String sortType) {

        List<Attraction> attractions;

        if (filterType == null && sortType == null) {

            attractions = attractionRepository.findAll();

        } else if (filterType == null) {

            Sort _sort = Sort.by(Sort.Direction.fromString(sortType), "name");
            attractions = attractionRepository.findAll(_sort);

        } else if (sortType == null) {

            attractions = attractionRepository
                    .findAllByAttractionType(AttractionType.valueOf(filterType.toUpperCase()));
        } else {

            Sort _sort = Sort.by(Sort.Direction.fromString(sortType), "name");
            attractions = attractionRepository
                    .findAllByAttractionType(AttractionType.valueOf(filterType.toUpperCase()), _sort);
        }

        return attractions.stream()
                .map(attractionMapper::toAttractionResponseDto)
                .collect(Collectors.toList());
    }

    public List<AttractionDto> getAllAttractionsByLocality(LocalityDto localityDto) {

        return localityRepository.findByRegionContainingAndSettlementContaining(
                    localityDto.region(),
                    localityDto.settlement()
                ).stream()
                .flatMap(locality -> locality.getAttractions().stream())
                .map(attractionMapper::toAttractionDto)
                .collect(Collectors.toList());
    }

    public AttractionResponseDto addAttractionToLocality(
            Long localityId,
            AttractionDto attractionDto
    ) {
        return localityRepository.findById(localityId)
                .map(locality -> {
                    Attraction attraction;
                    try {
                        attraction = attractionMapper.toAttraction(attractionDto);
                    } catch (ParseException e) {
                        throw new InvalidDateFormatException("creationDate", "Given date format does not correspond " +
                                "to what was expected");
                    }
                    attraction.setLocality(locality);
                    attractionRepository.save(attraction);
                    return attractionMapper.toAttractionResponseDto(attraction);
                })
                .orElseThrow(() -> new NotExistingParameterException("localityId", "Given unknown locality. Please, " +
                        "create locality with given ID first")); // TODO: ID -> field values
    }

    public AttractionResponseDto updateAttractionShortDescription(
            Long attractionId,
            AttractionShortDescriptionDto shortDescription
    ) {
        return attractionRepository.findById(attractionId)
                .map(attraction -> {
                    Attraction attractionShortDescription = attractionMapper.toAttraction(shortDescription);
                    attraction.setShortDescription(attractionShortDescription.getShortDescription());
                    attractionRepository.save(attraction);
                    return attractionMapper.toAttractionResponseDto(attraction);
                })
                .orElseThrow(() -> new NotExistingParameterException("attractionId", "Given unknown attraction. " +
                        "Please, create attraction with given ID first")); //TODO: ID -> field values
    }

    public void deleteAttraction(Long id) {

        attractionRepository.deleteById(id);
    }

}
