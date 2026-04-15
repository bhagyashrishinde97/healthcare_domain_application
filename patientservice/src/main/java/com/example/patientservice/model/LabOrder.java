package com.example.patientservice.model;

import com.example.patientservice.dto.response.LabOrderResponseDto;
import com.example.patientservice.enums.LabStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "lab_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LabOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @Column(nullable = false, length = 100)
    private String testName;

    @Column(name = "test_description", length = 1000)
    private String testDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LabStatus labStatus;

    @Column(nullable = false, length = 1000)
    private String result;


}