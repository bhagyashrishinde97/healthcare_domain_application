//package com.example.healthcare.model;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//@Entity
//@Table(name="vitals")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Vitals extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//   @ManyToOne
//    @JoinColumn(name="encounter_id",nullable = false)
//    private Encounter encounter;
//    private double height;
//    private double weight;
//    @Column(length = 20)
//    private String testName;
//    private String testDescription;
//    private int pulseRate;
//}
