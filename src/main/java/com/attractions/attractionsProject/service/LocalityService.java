package com.attractions.attractionsProject.service;

import com.attractions.attractionsProject.dtos.LocalityDto;
import com.attractions.attractionsProject.mappers.LocalityMapper;
import com.attractions.attractionsProject.model.Locality;
import com.attractions.attractionsProject.repository.LocalityRepository;
import org.springframework.stereotype.Service;

@Service
public class LocalityService {

    private final LocalityRepository localityRepository;
    private final LocalityMapper localityMapper;

    public LocalityService(
            LocalityRepository localityRepository,
            LocalityMapper localityMapper
    ) {
        this.localityRepository = localityRepository;
        this.localityMapper = localityMapper;
    }

    public LocalityDto createLocality(LocalityDto localityDto) {

        Locality locality = localityMapper.toLocality(localityDto);
        Locality createdLocality = localityRepository.save(locality);

        return localityMapper.toLocalityDto(createdLocality);
    }

}
