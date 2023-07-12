package com.digital.api.digital_booking.services;

import com.digital.api.digital_booking.models.Category;
import com.digital.api.digital_booking.repositories.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

    private final ICategoryRepository categoryRepository;

    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category updateCategoryById(Long idCategory, Category category){
        Category categoryToUpdate = categoryRepository.findByIdCategory(idCategory);

        if (categoryToUpdate != null) {
            if (category.getNameCategory() != null) {
                categoryToUpdate.setNameCategory(category.getNameCategory());
            }
            if (category.getDescriptionCategory() != null) {
                categoryToUpdate.setDescriptionCategory(category.getDescriptionCategory());
            }
            if (category.getImage() != null) {
                categoryToUpdate.setImage(category.getImage());

            }
            return categoryRepository.save(categoryToUpdate);


        } else {
            return null;
        }
    }

    public Category getCategoryById(Long idCategory){
        return categoryRepository.findByIdCategory(idCategory);
    }

    public String deleteCategory(Long idCategory){
        try {
            if (categoryRepository.findByIdCategory(idCategory) == null) {
                return "Category with id " + idCategory + " don't exist in database";
            }
            categoryRepository.deleteById(idCategory);
            return "Category with id " + idCategory + " deleted";
        }catch (DataIntegrityViolationException e){
            return "La categoria con id " + idCategory + " viola la integridad de la base de datos";
        }
        catch (Exception e) {
            return "Category with id " + idCategory + " not found";
        }
    }

    public Iterable<Category> getAll(){
        return categoryRepository.findAll();
    }

}
