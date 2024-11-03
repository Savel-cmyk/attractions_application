package com.attractions.attractionsProject.repository;

import com.attractions.attractionsProject.model.Attraction;
import com.attractions.attractionsProject.model.AttractionType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    List<Attraction> findAllByAttractionType(AttractionType attractionType, Sort sort);
    List<Attraction> findAllByAttractionType(AttractionType attractionType);
}
