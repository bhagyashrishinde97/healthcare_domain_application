//package com.example.healthcare.model;
//
//import com.example.healthcare.enums.LabStatus;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//@Data
//@Entity
//@Table(name="laborders")
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class LabOrder extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//   private int id;
//    @ManyToOne
//    @JoinColumn(name = "encounter_id",nullable = false)
//    private Encounter encounter;
//    @Column(nullable = false,length = 100)
//    private String testName;
//    @Column(name = "test_description",length = 1000)
//    private String testDescription;
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private LabStatus labStatus;
//    @Column(nullable = false,length = 1000)
//    private String result;
//
//}
