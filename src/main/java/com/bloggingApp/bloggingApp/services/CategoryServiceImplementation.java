package com.bloggingApp.bloggingApp.services;

import com.bloggingApp.bloggingApp.entities.Category;
import com.bloggingApp.bloggingApp.exceptions.ResourceNotFoundException;
import com.bloggingApp.bloggingApp.payloads.CategoryDto;
import com.bloggingApp.bloggingApp.repositories.CategoryRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category addedCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(addedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category", "Category Id", categoryId));
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryTitle(category.getCategoryTitle());
        Category updatedCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category", "Category Id", categoryId));
        this.categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category", "Category Id", categoryId));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        return categories.stream().map(category -> this.modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }
}
