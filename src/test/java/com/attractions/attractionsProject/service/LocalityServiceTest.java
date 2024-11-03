package com.attractions.attractionsProject.service;

import com.attractions.attractionsProject.dtos.LocalityDto;
import com.attractions.attractionsProject.mappers.LocalityMapper;
import com.attractions.attractionsProject.model.Locality;
import com.attractions.attractionsProject.repository.LocalityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocalityServiceTest {

    @InjectMocks
    private LocalityService localityService;

    @Mock
    private LocalityRepository localityRepository;
    @Mock
    private LocalityMapper localityMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSuccessfullyCreateLocalityEntity() {

        LocalityDto localityDto = new LocalityDto(
                "Москва",
                "Москва"
        );

        Locality localityEntity = new Locality();
        localityEntity.setSettlement("Москва");
        localityEntity.setRegion("Москва");

        Locality createdLocalityEntity = new Locality();
        createdLocalityEntity.setId(1L);
        createdLocalityEntity.setSettlement("Москва");
        createdLocalityEntity.setRegion("Москва");

        when(localityMapper.toLocality(localityDto))
                .thenReturn(localityEntity);
        when(localityRepository.save(localityEntity))
                .thenReturn(createdLocalityEntity);
        when(localityMapper.toLocalityDto(createdLocalityEntity))
                .thenReturn(localityDto);

        LocalityDto localityResponseDto = localityService.createLocality(localityDto);

        assertEquals(localityDto.settlement(), localityResponseDto.settlement());
        assertEquals(localityDto.region(), localityResponseDto.region());

        verify(localityMapper, times(1))
                .toLocality(localityDto);
        verify(localityRepository, times(1))
                .save(localityEntity);
        verify(localityMapper, times(1))
                .toLocalityDto(createdLocalityEntity);
    }
}