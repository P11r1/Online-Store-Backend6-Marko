package com.example.onlinestorebackend.repositories;

import com.example.onlinestorebackend.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Bahadir Tasli
 * @Date 3/23/2023
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    Optional<Category> findById(Long id);
}
