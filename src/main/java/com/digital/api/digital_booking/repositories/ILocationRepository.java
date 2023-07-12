package com.digital.api.digital_booking.repositories;

import com.digital.api.digital_booking.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationRepository extends JpaRepository<Location, Long> {
    Location findByIdLocation(Long idLocation);
}
