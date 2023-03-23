package com.example.onlinestorebackend.services;

import com.example.onlinestorebackend.exceptions.CategoryNotFoundException;
import com.example.onlinestorebackend.models.Category;

import java.util.List;

/**
 * @author Bahadir Tasli
 * @Date 3/23/2023
 */
public interface CategoryService {
    /**
     * To create a new Category
     *
     * @param category category
     */
    void createCategory(Category category);

    /**
     * To find a Category by name
     *
     * @param name Category name
     * @return Category
     */
    Category findCategoryByName(String name) throws CategoryNotFoundException;

    /**
     * To find all Categories
     *
     * @return a list of Categories
     */
    List<Category> findAllCategories();

    /**
     * To delete a Category by name
     *
     * @param name Category name
     */
    void deleteCategoryByName(String name) throws CategoryNotFoundException;

    /**
     * To restore a Category by name
     *
     * @param name Category name
     */
    void restoreCategoryByName(String name) throws CategoryNotFoundException;
}