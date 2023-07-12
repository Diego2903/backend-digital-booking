package com.digital.api.digital_booking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "images")
@Data

public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto increment
    @Column(unique = true, nullable = false)
    private Long idImages;

    @Column(unique = true)
    private String nameImage;

    @Column(columnDefinition = "LONGTEXT")
    private String urlImage;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore // Ignora la propiedad 'product' durante la serialización JSON
    private Product product;
}
