package com.example.patientservice.repository;

import com.example.patientservice.model.Vitals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VitalsRepository extends JpaRepository<Vitals,Long> {
}
