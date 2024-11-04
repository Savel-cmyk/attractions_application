package com.attractions.attractionsProject.mappers;

import com.attractions.attractionsProject.dtos.AttractionDto;
import com.attractions.attractionsProject.dtos.AttractionResponseDto;
import com.attractions.attractionsProject.dtos.AttractionShortDescriptionDto;
import com.attractions.attractionsProject.exception.InvalidDateFormatException;
import com.attractions.attractionsProject.model.Attraction;
import com.attractions.attractionsProject.model.AttractionType;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class AttractionMapper {

    private final LocalityMapper localityMapper;

    public AttractionMapper(LocalityMapper localityMapper) {
        this.localityMapper = localityMapper;
    }

    public Attraction toAttraction(AttractionDto attractionDto) {

        Attraction attraction = new Attraction();
        attraction.setName(attractionDto.name());

        SimpleDateFormat df = new SimpleDateFormat("yyyyy.MM.dd");
        java.util.Date utilDate;
        try {
            utilDate = df.parse(attractionDto.replaceDateEraWithMinusIfBC());
        } catch (ParseException ex) {
            throw new InvalidDateFormatException("creationDate", "Given date format does not correspond " +
                    "to what was expected");
        }
        java.sql.Date date = new java.sql.Date(utilDate.getTime());
        attraction.setCreationDate(date);

        attraction.setShortDescription(attractionDto.shortDescription());
        attraction.setAttractionType(AttractionType.valueOf(attractionDto.attractionType()));

        return attraction;
    }

    public Attraction toAttraction(AttractionShortDescriptionDto attractionDto) {

        Attraction attraction = new Attraction();
        attraction.setShortDescription(attractionDto.shortDescription());

        return attraction;
    }

    public AttractionDto toAttractionDto(Attraction attraction) {

        Date creationDate = attraction.getCreationDate();
        String date;
        if (creationDate == null) {
            date = "-";
        } else {
            DateFormat df = new SimpleDateFormat("yyyy.MM.dd GG");
            date = df.format(creationDate);
        }

        return new AttractionDto(
                attraction.getName(),
                date,
                attraction.getShortDescription(),
                attraction.getAttractionType().toString().toLowerCase()
        );
    }

    public AttractionResponseDto toAttractionResponseDto(Attraction attraction) {

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd GG");

        return new AttractionResponseDto(
                attraction.getName(),
                df.format(attraction.getCreationDate()),
                attraction.getShortDescription(),
                attraction.getAttractionType(),
                localityMapper.toLocalityDto(attraction.getLocality())
                );
    }
}
