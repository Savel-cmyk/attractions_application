package com.attractions.attractionsProject.mappers;

import com.attractions.attractionsProject.dtos.LocalityDto;
import com.attractions.attractionsProject.model.Locality;
import org.springframework.stereotype.Service;

@Service
public class LocalityMapper {

    public Locality toLocality(LocalityDto localityDto) {

        Locality locality = new Locality();
        locality.setSettlement(localityDto.settlement());
        locality.setRegion(localityDto.region());

        return locality;
    }

    public LocalityDto toLocalityDto(Locality locality) {

        return new LocalityDto(
                locality.getSettlement(),
                locality.getRegion()
                );
    }

}
