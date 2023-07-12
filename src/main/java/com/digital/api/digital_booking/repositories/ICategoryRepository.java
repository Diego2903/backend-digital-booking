package com.digital.api.digital_booking.repositories;

import com.digital.api.digital_booking.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Category findByIdCategory(Long idCategory);
}
