//package com.example.healthcare.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//@Data
//@Table(name="prescriptions")
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//public class Prescription extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name="encounter_id",nullable = false)
//    private Encounter encounter;
//    @Column(name="medicine_name",nullable = false,length = 100)
//    private String medicineName;
//    @Column(length = 50)
//    private String dosage;
//    @Column(length = 50)
//    private String frequency;
//    private String duration;
//    @Column(length = 500)
//    private String instructions;
//}
