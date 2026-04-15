package com.example.patientservice.repository;

import com.example.patientservice.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    Optional<Clinic> findByContactEmail(String contactEmail);

    List<Clinic> findByIsActive(Boolean isActive);
}