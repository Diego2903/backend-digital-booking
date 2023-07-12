package com.digital.api.digital_booking.repositories;

import com.digital.api.digital_booking.models.Politics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPoliticsRepository extends JpaRepository<Politics, Long> {
    Politics findByIdPolitics(Long idPolitics);
}
