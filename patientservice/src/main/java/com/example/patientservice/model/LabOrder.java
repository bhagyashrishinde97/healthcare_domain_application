package com.example.patientservice.model;

import com.example.patientservice.dto.LabOrderResponseDto;
import com.example.patientservice.enums.LabStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@Table(name="lab_orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabOrder extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @ManyToOne
    @JoinColumn(name = "encounter_id",nullable = false)
    private Encounter encounter;
    @Column(nullable = false,length = 100)
    private String testName;
    @Column(name = "test_description",length = 1000)
    private String testDescription;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LabStatus labStatus;
    @Column(nullable = false,length = 1000)
    private String result;
    public LabOrderResponseDto toDto() {
        return LabOrderResponseDto.builder()
                .id(this.id)
                .testName(this.testName)
                .testDescription(this.testDescription).labStatus(this.labStatus)
                .result(this.result)
                .encounterId(this.encounter.getId())
                .build();
    }
}