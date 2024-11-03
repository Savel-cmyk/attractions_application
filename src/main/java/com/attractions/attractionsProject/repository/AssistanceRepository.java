package com.attractions.attractionsProject.repository;

import com.attractions.attractionsProject.model.Assistance;
import com.attractions.attractionsProject.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssistanceRepository extends JpaRepository<Assistance, Long> {
    List<Assistance> findByServiceTypeAndShortDescriptionContainingAndExecutorContaining(
            ServiceType serviceType,
            String shortDescription,
            String executor
    );
}
