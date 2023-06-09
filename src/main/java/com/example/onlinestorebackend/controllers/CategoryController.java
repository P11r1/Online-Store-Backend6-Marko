package com.example.onlinestorebackend.controllers;

import com.example.onlinestorebackend.exceptions.CategoryNotFoundException;
import com.example.onlinestorebackend.exceptions.SubCategoryNotFoundException;
import com.example.onlinestorebackend.models.Category;
import com.example.onlinestorebackend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Category Controller
 *
 * @author Marko
 * @Date 25/03/2023
 */

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String showCategoryListPage(Model model, @ModelAttribute("message") String message,
                                       @ModelAttribute("messageType") String messageType) {
        model.addAttribute("categories", categoryService.findAllCategories());
        return "category/list-category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategoryById(id);
            redirectAttributes.addFlashAttribute("message", String.format("Category(id=%d) deleted successfully!", id));
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/category";
        } catch (CategoryNotFoundException | SubCategoryNotFoundException e) {
            return handleException(redirectAttributes, e);
        }
    }

    @GetMapping("/restore/{id}")
    public String restoreCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.restoreCategoryById(id);
            redirectAttributes.addFlashAttribute("message", String.format("Category(id=%d) restored successfully!", id));
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/category";
        } catch (CategoryNotFoundException | SubCategoryNotFoundException e) {
            return handleException(redirectAttributes, e);
        }
    }

    @GetMapping("/create")
    public String showCreateCategoryPage(@ModelAttribute("category") Category category,
                                         @ModelAttribute("message") String message,
                                         @ModelAttribute("messageType") String messageType) {
        return "category/create-category";
    }

    @PostMapping
    public String createCategory(Category category, RedirectAttributes redirectAttributes) {
        try {
            Category searchCategory = categoryService.findCategoryByName(category.getName());
            redirectAttributes.addFlashAttribute("message", String.format("Category(%s) already exists!", category.getName()));
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/category/create-category";
        } catch (CategoryNotFoundException e) {
            categoryService.createCategory(category);
            redirectAttributes.addFlashAttribute("message", String.format("Category(%s) has been created successfully!", category.getName()));
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/category";
        }
    }

    @GetMapping("/update/{id}")
    public String showUpdateCategoryPage(@PathVariable Long id, RedirectAttributes redirectAttributes,
                                         @RequestParam(value = "category", required = false) Category category,
                                         Model model) {
        if (category == null) {
            try {
                model.addAttribute("category", categoryService.findCategoryById(id));
            } catch (CategoryNotFoundException e) {
                return handleException(redirectAttributes, e);
            }
        }
        return "category/update-category";
    }

    @PostMapping("/update")
    public String updateCategory(Category category, RedirectAttributes redirectAttributes) {
        try {
            categoryService.updateCategory(category);
            redirectAttributes.addFlashAttribute("message", String.format("Category(id=%d) has been updated successfully!", category.getId()));
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/category";
        } catch (CategoryNotFoundException e) {
            return handleException(redirectAttributes, e);
        }
    }

    // PRIVATE METHODS //
    private String handleException(RedirectAttributes redirectAttributes, Exception e) {
        redirectAttributes.addFlashAttribute("message", e.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("messageType", "error");
        return "redirect:/category";
    }
}
