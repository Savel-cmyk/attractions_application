package com.attractions.attractionsProject.mappers;

import com.attractions.attractionsProject.dtos.LocalityDto;
import com.attractions.attractionsProject.model.Locality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocalityMapperTest {

    private LocalityMapper localityMapper;

    @BeforeEach
    void setUp() {
        localityMapper = new LocalityMapper();
    }

    @Test
    public void shouldMapLocalityDtoToLocalityEntity() {

        LocalityDto localityDto = new LocalityDto(
                "Москва",
                "Москва"
        );

        Locality localityEntity = localityMapper.toLocality(localityDto);

        assertEquals(localityDto.settlement(), localityEntity.getSettlement());
        assertEquals(localityDto.region(), localityEntity.getRegion());
    }

    @Test
    public void shouldMapLocalityEntityToLocalityDto() {

        Locality localityEntity = new Locality();

        localityEntity.setSettlement("Москва");
        localityEntity.setRegion("Москва");

        LocalityDto localityDto = localityMapper.toLocalityDto(localityEntity);

        assertEquals(localityDto.settlement(), localityEntity.getSettlement());
        assertEquals(localityDto.region(), localityEntity.getRegion());
    }
}