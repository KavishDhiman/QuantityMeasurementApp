package com.app.quantitymeasurement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String operation;

    @Column(nullable = false)
    private double resultValue;

    @Column
    private String unit1;

    @Column
    private double value1;

    @Column
    private String unit2;

    @Column
    private double value2;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}