package com.digital.api.digital_booking.repositories;

import com.digital.api.digital_booking.models.Favorites;
import com.digital.api.digital_booking.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface IFavoriteRepository extends JpaRepository<Favorites, Long> {

    @Autowired

    @Query(value = "SELECT * FROM favorites WHERE user_id = ?1", nativeQuery = true)
    Favorites findByIdUser(Long idUser);

    @Query(value = "SELECT product_id  FROM favorites WHERE user_id = ?1", nativeQuery = true)
    Iterable<Long> findAllFavoritesByUser(Long idUser);




    @Query(value = "SELECT * FROM favorites WHERE id_favorites = ?1", nativeQuery = true)
    Favorites findByIdFavorite(Long idFavorite);

    //findByIdUserAndIdProduct
    @Query(value = "SELECT * FROM favorites WHERE user_id = ?1 AND product_id = ?2", nativeQuery = true)
    Favorites findByIdUserAndIdProduct(Long idUser, Long idProduct);

    void deleteByProduct_IdProduct(Long idProduct);

}
