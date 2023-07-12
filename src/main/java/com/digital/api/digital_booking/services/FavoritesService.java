package com.digital.api.digital_booking.services;

import com.digital.api.digital_booking.models.Favorites;
import com.digital.api.digital_booking.models.Product;
import com.digital.api.digital_booking.models.Users;
import com.digital.api.digital_booking.repositories.IFavoriteRepository;
import com.digital.api.digital_booking.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class FavoritesService {

    private final IFavoriteRepository favoritesRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private IUserRepository userRepository;


    public Favorites createFavorites(Favorites favorites){

        Product existProduct =  productService.getProductById(favorites.getProduct().getIdProduct());
        Users existUser = userRepository.findByIdUser(favorites.getUser().getIdUser());

        //si el usuario ya tiene el producto en favoritos, no se puede volver a agregar
        Favorites existFavorite = favoritesRepository.findByIdUserAndIdProduct(favorites.getUser().getIdUser(), favorites.getProduct().getIdProduct());
        if(existFavorite != null){
            throw new RuntimeException("Product already in favorites");
        }

        if(existProduct == null){
            throw new RuntimeException("Product not found");
        }
        if(existUser == null){
            throw new RuntimeException("User not found");
        }
        favorites.setProduct(existProduct);
        favorites.setUser(existUser);



        return favoritesRepository.save(favorites);


    }

    public Iterable<Product> getAllFavoritesByUser(Long idUser){
        Iterable<Long> idFavorites = favoritesRepository.findAllFavoritesByUser(idUser);
        List<Long> productIdList = StreamSupport.stream(idFavorites.spliterator(), false).toList();
        return productService.getAllProductsById(productIdList);
}

    public String deleteFavorites(Long idUser,Long idProduct){

        Favorites existFavorite = favoritesRepository.findByIdUserAndIdProduct(idUser, idProduct);
        if(existFavorite == null){
            throw new RuntimeException("Favorite not found");
        }
        favoritesRepository.delete(existFavorite);
        return "Favorite deleted";

    }

}
