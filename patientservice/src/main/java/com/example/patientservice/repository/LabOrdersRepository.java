package com.example.patientservice.repository;

import com.example.patientservice.model.LabOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabOrdersRepository extends JpaRepository<LabOrder, Long> {
}