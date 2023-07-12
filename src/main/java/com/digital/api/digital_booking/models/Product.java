package com.digital.api.digital_booking.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.util.List;

@Entity // this is a table spring boot will create
@Table(name = "product") // this is the name of the table
@Data // all methods are getters and setters
public class Product {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto increment
    @Column(unique = true, nullable = false)
    private Long idProduct;

    @Column(unique = true)
    private String nameProduct;

    @Column(columnDefinition = "LONGTEXT")
    private String descriptionProduct;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "idCategory")
    private Category category;
    private String duration;

    @ManyToOne
    @JoinColumn(name = "idPolitics")
    private Politics politics;

    @ManyToOne
    @JoinColumn(name = "idCity")
    private City ciudad;

    @OneToOne
    @JoinColumn(name = "idLocation")
    private Location location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Images> images;

    private BigDecimal   price;
    private String turistGuide;
}
