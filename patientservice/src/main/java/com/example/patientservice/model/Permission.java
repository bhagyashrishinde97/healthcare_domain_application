package com.example.patientservice.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission_name", unique = true, nullable = false)
    private String permissionName;

    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<Roles> roles = new HashSet<>();
}