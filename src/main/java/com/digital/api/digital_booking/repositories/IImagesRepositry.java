package com.digital.api.digital_booking.repositories;

import com.digital.api.digital_booking.models.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImagesRepositry extends JpaRepository<Images, Long> {
    void deleteByProduct_IdProduct(Long idProduct);
}
