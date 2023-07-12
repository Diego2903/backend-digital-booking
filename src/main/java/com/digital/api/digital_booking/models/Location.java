package com.digital.api.digital_booking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "location")
@Data

public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto increment
    @Column(unique = true, nullable = false)
    private Long idLocation;

    @Column(unique = true)
    private Double latitude;

    @Column(unique = true)
    private Double longitude;

    @Column(columnDefinition = "LONGTEXT")
    private String urlLocation;
}
