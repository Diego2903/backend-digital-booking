package com.digital.api.digital_booking.repositories;

import com.digital.api.digital_booking.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface ICityRepository extends JpaRepository<City, Long> {
    City findByIdCity(Long idCity);
    City findByNameCity(String nameCity);


}
