package com.pst.exam.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "car", schema = "exam")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "color", nullable = false, columnDefinition = "TEXT")
    private String color;

    @Column(name = "length", nullable = false, columnDefinition = "DECIMAL")
    private Double length;

    @Column(name = "weight", nullable = false, columnDefinition = "DECIMAL")
    private Double weight;

    @Column(name = "velocity", nullable = false, columnDefinition = "DECIMAL")
    private Double velocity;
}
