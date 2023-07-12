package com.digital.api.digital_booking.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "city")
@Data

public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto increment
    @Column(unique = true, nullable = false)
    private Long idCity;

    @Column(unique = true)
    private String nameCity;

    @Column(columnDefinition = "LONGTEXT")
    private String nameCountry;

}
