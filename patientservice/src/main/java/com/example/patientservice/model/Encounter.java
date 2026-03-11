//package com.example.healthcare.model;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Data
//@Entity
//@Table(name="encounters")
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Encounter extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @OneToOne
//    @JoinColumn(name="appoinment_id",nullable = false,unique = true)
//    private Appointment appointment;
//    @Column(length = 500)
//    private String dignosis;
//    @Column(length=1000)
//    private String notes;
//    @Column(name="encounter_date", nullable = false)
//    private LocalDateTime encounterDate;
//    @OneToMany(mappedBy = "encounter",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Prescription> prescriptions;
//    @OneToMany(mappedBy ="laborder", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<LabOrder> labOrders;
//    @OneToMany(mappedBy = "vitals",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Vitals> vitals;
//}
