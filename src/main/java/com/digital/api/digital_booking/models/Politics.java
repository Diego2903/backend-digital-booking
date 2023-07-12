package com.digital.api.digital_booking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "politics")
@Data
public class Politics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto increment
    @Column(unique = true, nullable = false)
    private Long idPolitics;

    @Column(columnDefinition = "LONGTEXT")
    private String cancellationPolicy;

    @Column(columnDefinition = "LONGTEXT")
    private String responsibilityPolicy;

    @Column(columnDefinition = "LONGTEXT")
    private String refundPolicy;

    @Column(columnDefinition = "LONGTEXT")
    private String changesPolicy;

    @Column(columnDefinition = "LONGTEXT")
    private String privacyPolicy;


}
