package com.attractions.attractionsProject.repository;

import com.attractions.attractionsProject.model.Locality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalityRepository extends JpaRepository<Locality, Long> {
    List<Locality> findByRegionContainingAndSettlementContaining(
        String region,
        String settlement
    );
}
