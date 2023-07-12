package com.digital.api.digital_booking.models;


import com.digital.api.digital_booking.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;

@Entity // this is a table spring boot will create
@Table(name = "bookings") // this is the name of the table
@Getter
@Setter
@ToString
@RequiredArgsConstructor // all methods are getters and setters

public class Booking {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto increment
    @Column(unique = true, nullable = false)
    private Long idBooking;

//    @Column(unique = true)
    private Date initialDate;

//    @Column(unique = true)
    private Date finishDate;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private Users user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Booking booking = (Booking) o;
        return idBooking != null && Objects.equals(idBooking, booking.idBooking);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
