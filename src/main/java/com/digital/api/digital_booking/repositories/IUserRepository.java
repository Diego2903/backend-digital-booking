package com.digital.api.digital_booking.repositories;

import com.digital.api.digital_booking.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    Users findByIdUser(Long idUser);

    @Query("from Users u where u.name =:name")
    Users getByName(@Param("name") String name);

    Users findByEmail(String email);
}


