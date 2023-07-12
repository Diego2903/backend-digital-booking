package com.digital.api.digital_booking.repositories;

import com.digital.api.digital_booking.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Product findByNameProduct(String nameProduct);

    Page<Product> findByCategory_IdCategory(Long categoryId, Pageable pageable);

    Product findByIdProduct(Long idProduct);

    Page<Product> findByCiudad_IdCity(Long idCity, Pageable pageable);

    Page<Product> findByCiudad_NameCity(String nameCity, Pageable pageable);

    Page<Product> findByCiudad_NameCityAndCategory_IdCategory(String nameCity, Long categoryId, Pageable pageable);
    @Query("SELECT p FROM Product p " +
            "WHERE p.idProduct IN (" +
            "    SELECT b1.product.idProduct " +
            "    FROM Booking b1 " +
            "    LEFT JOIN Booking b2 ON b1.product.idProduct = b2.product.idProduct " +
            "    WHERE b2.initialDate IS NULL " +
            "        AND b1.initialDate <= ?2 " +
            "        AND b1.finishDate >= ?1 " +
            "    UNION " +
            "    SELECT p2.idProduct " +
            "    FROM Product p2 " +
            "    WHERE NOT EXISTS (" +
            "        SELECT 1 " +
            "        FROM Booking b " +
            "        WHERE b.product.idProduct = p2.idProduct " +
            "            AND b.initialDate <= ?2 " +
            "            AND b.finishDate  >= ?1 " +
            "    )" +
            ")")

    Page<Product> findByDateRange(Date initialDate, Date finishDate, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.ciudad.idCity = ?3 AND p.idProduct IN (" +
            "    SELECT b1.product.idProduct " +
            "    FROM Booking b1 " +
            "    LEFT JOIN Booking b2 ON b1.product.idProduct = b2.product.idProduct " +
            "    WHERE b2.initialDate IS NULL " +
            "        AND b1.initialDate <= ?2 " +
            "        AND b1.finishDate >= ?1 " +
            "    UNION " +
            "    SELECT p2.idProduct " +
            "    FROM Product p2 " +
            "    WHERE NOT EXISTS (" +
            "        SELECT 1 " +
            "        FROM Booking b " +
            "        WHERE b.product.idProduct = p2.idProduct " +
            "            AND b.initialDate <= ?2 " +
            "            AND b.finishDate  >= ?1 " +
            "    )" +
            ")")
    Page<Product> findByDateRange_City(Date initialDate, Date finishDate, Long idCity , Pageable pageable);

    //findAllByIdProduct
    @Query(value = "SELECT * FROM product WHERE id_product = ?1", nativeQuery = true)
    List<Product> findAllByIdProduct(Long idProduct);

    @Query(value = "DELETE FROM product WHERE id_product = ?1", nativeQuery = true)
    @Modifying
    void deleteByIdProduct(Long idProduct);



}
