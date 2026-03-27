package com.example.patientservice.repository;

import com.example.patientservice.model.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncounterRepository extends JpaRepository<Encounter, Long> {
}