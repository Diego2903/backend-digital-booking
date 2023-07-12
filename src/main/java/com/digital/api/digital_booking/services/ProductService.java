package com.digital.api.digital_booking.services;


import com.digital.api.digital_booking.exceptions.BadRequestException;
import com.digital.api.digital_booking.exceptions.CategoryNotFoundException;
import com.digital.api.digital_booking.exceptions.NotFoundException;
import com.digital.api.digital_booking.models.*;
import com.digital.api.digital_booking.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    IProductRepository productRepository;
    @Autowired
    ICategoryRepository categoryRepository;
    @Autowired
    ICityRepository cityRepository;
    @Autowired
    ILocationRepository locationRepository;
    @Autowired
    IPoliticsRepository politicsRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    IFavoriteRepository favoriteRepository;

    @Autowired
    IImagesRepositry imagesRepositry;

    public Product createProduct(Product product) {
        System.out.println("product: " + product);
        if (productRepository.findByNameProduct(product.getNameProduct()) != null) {            //return status 400 bad request
            throw new BadRequestException("El producto ya existe.");

        } else {
            System.out.println("product: " + product);
            Category category = categoryRepository.findByIdCategory(product.getCategory().getIdCategory());
            City city = cityRepository.findByIdCity(product.getCiudad().getIdCity());
            Politics politics = politicsRepository.findByIdPolitics(product.getPolitics().getIdPolitics());
            //traer location de product sin id y hacer save
            Location location = new Location();
            location.setLatitude(product.getLocation().getLatitude());
            location.setLongitude(product.getLocation().getLongitude());
            location.setUrlLocation(product.getLocation().getUrlLocation());

            location = locationRepository.save(location);
            if (category == null) {
                throw new CategoryNotFoundException("La categoría no existe.");
            }

            if (city == null) {
                throw new NotFoundException("La ciudad no existe.");
            }

            if(politics == null){
                throw new NotFoundException("La política no existe.");
            }

            product.setCategory(category);
            product.setCiudad(city);
            product.setLocation(location);
            product.setPolitics(politics);

            for(int i = 0; i < product.getImages().size(); i++) {
                System.out.println("product.getImages().get(i): " + product.getImages().get(i));
                product.getImages().get(i).setProduct(product);
            }

            return productRepository.save(product);
        }

    }
    public PageResponse<Product> getAll(int pageNumber, int pageSize, String sortField, String sortOrder, Long categoryId, String nameCity, Date startDate, Date endDate) {
        // Definir opciones de ordenación si es necesario
        Sort sort = Sort.by(sortField);
        if ("desc".equals(sortOrder)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        // Crear un objeto PageRequest con el tamaño de página, el número de página y las opciones de ordenación
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        // Ejecutar la consulta paginada utilizando el método findAll() de Spring Data JPA y el objeto PageRequest
        Page<Product> productPage;

        System.out.println("categoryId: " + categoryId);
        productPage = productRepository.findAll(pageRequest);

        if (categoryId != null) {
            // Filtrar por categoría si se proporciona un ID de categoría válido
            productPage = productRepository.findByCategory_IdCategory(categoryId, pageRequest);
        }
        if (nameCity == null && startDate != null && endDate != null) {
            productPage = productRepository.findByDateRange(startDate, endDate, pageRequest);
            System.out.println("filtro por fechas");

        }

        System.out.println("idCity: " + nameCity);
        System.out.println("idCity: " + startDate);
        System.out.println("idCity: " + endDate);

        if (nameCity != null && startDate != null && endDate != null) {
            // Filtrar por categoría si se proporciona un ID de categoría válido
            City city = cityRepository.findByNameCity(nameCity);
            Long idCity = city.getIdCity();
            System.out.println("idCity: " + idCity);
            System.out.println("filtro por fechas y ciudad");
            productPage = productRepository.findByDateRange_City(startDate, endDate, idCity, pageRequest);
            //si no se proporciona un id de categoria valido, se filtra por ciudad
        }else if(nameCity != null && startDate == null && endDate == null){
            productPage = productRepository.findByCiudad_NameCity(nameCity, pageRequest);
            System.out.println("filtro por ciudad");
        }




        // Obtener la lista de productos del objeto Page
        List<Product> products = productPage.getContent();

        // Devolver el objeto PageResponse
        return new PageResponse<Product>(
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.getNumber(),
                productPage.getSize(),
                products
        );
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        try {
            bookingRepository.deleteByProduct_IdProduct(id);
            favoriteRepository.deleteByProduct_IdProduct(id);
            imagesRepositry.deleteByProduct_IdProduct(id);


            productRepository.deleteByIdProduct(id);



            return true;
        } catch (Exception err) {
            System.out.println("err: " + err);
            return false;
        }
    }
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    public Product updateProductById(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new NotFoundException("El producto no existe."));

        if (updatedProduct.getNameProduct() != null) {
            existingProduct.setNameProduct(updatedProduct.getNameProduct());
        }

        if (updatedProduct.getDescriptionProduct() != null) {
            existingProduct.setDescriptionProduct(updatedProduct.getDescriptionProduct());
        }

        if (updatedProduct.getImages() != null) {
            existingProduct.setImages(updatedProduct.getImages());
        }
        if (updatedProduct.getCategory() != null) {
            System.out.println("updatedProduct.getCategory(): " + updatedProduct.getCategory());
            Category category = categoryRepository.findByIdCategory(updatedProduct.getCategory().getIdCategory());
            existingProduct.setCategory(category);
        }
        if (updatedProduct.getDuration() != null) {
            existingProduct.setDuration(updatedProduct.getDuration());
        }
        if (updatedProduct.getPrice() != null) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }
        if (updatedProduct.getTuristGuide() != null) {
            existingProduct.setTuristGuide(updatedProduct.getTuristGuide());
        }
        if (updatedProduct.getLocation() != null) {

            Location location = locationRepository.findByIdLocation(existingProduct.getLocation().getIdLocation());
            location.setLatitude(updatedProduct.getLocation().getLatitude());
            location.setLongitude(updatedProduct.getLocation().getLongitude());
            location.setUrlLocation(updatedProduct.getLocation().getUrlLocation());
            locationRepository.save(location);

        }

        return productRepository.save(existingProduct);
    }


    public List<Product> getAllProductsRandom() {
        List<Product> products = productRepository.findAll();
        Collections.shuffle(products);
        return products;
    }
    public Product updateProductImage(Long id, String image) {
        Product existingProduct = productRepository.findByIdProduct(id);
        if (existingProduct == null) {
            throw new NotFoundException("El producto no existe.");
        }
        Images newImage = new Images();
        newImage.setNameImage(image);
        newImage.setUrlImage(image);;
        newImage.setProduct(existingProduct);
        existingProduct.getImages().add(newImage);
        return productRepository.save(existingProduct);

    }

    //    getAllProductsById
    public List<Product> getAllProductsById(List<Long> ids) {
        return productRepository.findAllById(ids);
    }

}
