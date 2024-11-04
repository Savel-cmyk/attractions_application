package com.attractions.attractionsProject.mappers;

import com.attractions.attractionsProject.dtos.AttractionDto;
import com.attractions.attractionsProject.model.Attraction;
import com.attractions.attractionsProject.model.AttractionType;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class AttractionMapperUnitTests {

    private AttractionMapper attractionMapper;
    private LocalityMapper localityMapper;

    @BeforeEach
    void setUp() {
        localityMapper = new LocalityMapper();
        attractionMapper = new AttractionMapper(localityMapper);
    }

    @Test
    public void shouldMapAttractionDtoToAttractionEntity() {

        AttractionDto attractionDto = new AttractionDto(
                "Кремль",
                "1495.12.31 AD",
                "Крепость в центре Москвы и древнейшая её часть, главный общественно-политический и " +
                        "историко-художественный комплекс города. Одно из самых известных архитектурных сооружений в " +
                        "мире.",
                "ARCHITECTURAL"
        );

        Attraction attractionEntity = attractionMapper.toAttraction(attractionDto);

        Attraction expectedAttractionEntity = new Attraction();
        expectedAttractionEntity.setName(attractionDto.name());

        Calendar cln = Calendar.getInstance();
        cln.set(Calendar.MONTH, 11);
        cln.set(Calendar.DATE, 31);
        cln.set(Calendar.YEAR, 1495);
        cln.set(Calendar.MILLISECOND, 0);
        cln.set(Calendar.SECOND, 0);
        cln.set(Calendar.MINUTE, 0);
        cln.set(Calendar.HOUR, 0);
        java.sql.Date date = new java.sql.Date(cln.getTime().getTime());
        expectedAttractionEntity.setCreationDate(date);

        expectedAttractionEntity.setShortDescription(attractionDto.shortDescription());
        expectedAttractionEntity.setAttractionType(AttractionType.ARCHITECTURAL);

        assertEquals(attractionEntity.getName(), expectedAttractionEntity.getName());

        long actualDate = attractionEntity.getCreationDate().getTime();
        long expectedDate = expectedAttractionEntity.getCreationDate().getTime();
        System.out.println(actualDate - expectedDate);
        assertTrue(Math.abs(actualDate - expectedDate) < 1000,
                "Dates aren't close enough to each other!");

        assertEquals(attractionEntity.getShortDescription(), expectedAttractionEntity.getShortDescription());
        assertEquals(attractionEntity.getAttractionType(), expectedAttractionEntity.getAttractionType());
    }

    @Test
    public void shouldTryToMapAttractionDtoToAttractionEntity_AndThen_ThrowInvalidDateFormatExceptionWhenDateFormatParsingFails() {

    }
}