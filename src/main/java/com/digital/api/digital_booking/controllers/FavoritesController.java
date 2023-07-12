package com.digital.api.digital_booking.controllers;

import com.digital.api.digital_booking.models.Favorites;
import com.digital.api.digital_booking.models.Product;
import com.digital.api.digital_booking.services.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/favorites", produces = "application/json")
public class FavoritesController {
    @Autowired
    private FavoritesService favoritesService;

    @GetMapping(path = "/getByUser/{idUser}", produces = "application/json")
    public Iterable<Product> getFavoritesByUser(@PathVariable("idUser") Long idUser){
        return favoritesService.getAllFavoritesByUser(idUser);
    }

    @DeleteMapping(path = "/delete/{idUser}/{idProduct}", produces = "application/json")
    public String deleteFavorite(@PathVariable("idUser") Long idUser, @PathVariable("idProduct") Long idProduct) {
        return favoritesService.deleteFavorites(idUser, idProduct);
    }



    @PostMapping(path = "/create", produces = "application/json")
    public Favorites createFavorites(@RequestBody Favorites favorites){
        return favoritesService.createFavorites(favorites);
    }


}
