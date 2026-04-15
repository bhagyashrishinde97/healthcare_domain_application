package com.example.patientservice.repository;

import com.example.patientservice.model.LabOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabOrdersRepository extends JpaRepository<LabOrder, Long> {

    List<LabOrder> findByEncounterId(Long encounterId);
}