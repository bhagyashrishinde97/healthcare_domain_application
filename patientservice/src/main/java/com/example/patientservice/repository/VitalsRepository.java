package com.example.patientservice.repository;

import com.example.patientservice.model.Vitals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VitalsRepository extends JpaRepository<Vitals, Long> {

    List<Vitals> findByEncounterId(Long encounterId);
}