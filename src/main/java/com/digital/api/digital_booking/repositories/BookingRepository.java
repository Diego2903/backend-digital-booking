package com.digital.api.digital_booking.repositories;

import com.digital.api.digital_booking.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository <Booking, Long> {
    Booking findByIdBooking(Long idBooking);
    void deleteByProduct_IdProduct(Long idProduct);

}
