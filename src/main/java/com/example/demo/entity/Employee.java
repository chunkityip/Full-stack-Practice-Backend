package com.example.demo.entity;

import com.example.demo.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeStatus status;

    // Set the default status to DRAFT when creating new employee
    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = EmployeeStatus.DRAFT;
        }
    }


}