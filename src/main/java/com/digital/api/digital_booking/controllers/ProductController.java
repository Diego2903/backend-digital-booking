package com.digital.api.digital_booking.controllers;

import com.digital.api.digital_booking.exceptions.BadRequestException;
import com.digital.api.digital_booking.exceptions.CategoryNotFoundException;
import com.digital.api.digital_booking.models.PageResponse;
import com.digital.api.digital_booking.models.Product;
import com.digital.api.digital_booking.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/product", produces = "application/json")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(path = "/getAll", produces = "application/json")
    @ResponseBody
    public PageResponse<Product> getAllProducts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "idProduct") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String nameCity,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate)  {
        return productService.getAll(pageNumber, pageSize, sortField, sortOrder, categoryId, nameCity, startDate, endDate);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            Product newProduct = productService.createProduct(product);
            return ResponseEntity.ok(newProduct);
        } catch (BadRequestException e) {
            String error = "Product with name " + product.getNameProduct() + " already exists";
            return ResponseEntity.badRequest().body(error);
        } catch (CategoryNotFoundException e) {
            String error = "Category not found for ID: " + product.getCategory().getIdCategory();
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        System.out.println("id: " + id);
        boolean ok = productService.deleteProduct(id);
        if (ok) {
            return "Product with id " + id + " deleted";
        } else {
            return "Product with id " + id + " not found";
        }
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return productService.updateProductById(id, product);
    }

    @GetMapping("/allRandom")
    public List<Product> getAllRandom() {
        return productService.getAllProductsRandom();
    }






}
