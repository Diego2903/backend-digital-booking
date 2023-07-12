package com.digital.api.digital_booking.controllers;

import com.digital.api.digital_booking.models.Category;
import com.digital.api.digital_booking.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/category", produces = "application/json")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(path = "/getAll", produces = "application/json" )
    @ResponseBody
    public Iterable<Category> getAllCategories() {
        return categoryService.getAll();
    }

    @PostMapping("/create")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/update/{id}")
    public Category updateCategory(@PathVariable("id") Long id, @RequestBody Category category) {
        return categoryService.updateCategoryById(id, category);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        String deleted = categoryService.deleteCategory(id);
        if (Objects.equals(deleted, "Category with id " + id + " deleted")){
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(deleted, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }



}
