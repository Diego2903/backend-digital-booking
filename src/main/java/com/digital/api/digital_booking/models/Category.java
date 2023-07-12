    package com.digital.api.digital_booking.models;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;

    @Entity
    @Table(name = "category")
    @Data
    public class Category {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)// auto increment
        @Column(unique = true, nullable = false)
        private Long idCategory;
        @Column(unique = true)
        private String nameCategory;
        @Column(columnDefinition = "LONGTEXT")
        private String descriptionCategory;
        private String image;

    }
