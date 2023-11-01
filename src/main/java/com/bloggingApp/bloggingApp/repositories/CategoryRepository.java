package com.bloggingApp.bloggingApp.repositories;

import com.bloggingApp.bloggingApp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
